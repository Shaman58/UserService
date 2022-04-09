package edu.shmonin.userservice.service;

import edu.shmonin.userservice.exception.EntityNotFoundException;
import edu.shmonin.userservice.model.Role;
import edu.shmonin.userservice.model.User;
import edu.shmonin.userservice.repository.UserRepository;
import edu.shmonin.userservice.security.UserPrincipal;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static edu.shmonin.userservice.exception.ExceptionMessage.USER;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oauthUser = super.loadUser(userRequest);
        var username = (String) oauthUser.getAttributes().get("login");
        var userPrincipal = userRepository.findByUsername(username)
                .map(user -> new UserPrincipal(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(toList()),
                        oauthUser.getAttributes()
                ));
        if (userPrincipal.isEmpty()) {
            var user = new User();
            user.setUsername(username);
            var userRole = new Role();
            userRole.setName("ROLE_USER");
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        } else {
            return userPrincipal.get();
        }
        return oauthUser;
    }

    public List<User> findAll() {
        log.debug("Get all users");
        return userRepository.findAll();
    }

    public User findById(Long id) {
        log.debug("Get user with id={}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER.getMessage(id)));
    }

    public User create(User user) {
        log.debug("Create user {}", user);
        return userRepository.save(user);
    }

    public User update(User user, Long id) {
        user.setId(id);
        log.debug("Update user {}", user);
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        log.debug("Delete user by id={}", id);
        findById(id);
        userRepository.deleteById(id);
    }
}
