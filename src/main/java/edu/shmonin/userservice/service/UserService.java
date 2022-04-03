package edu.shmonin.userservice.service;

import edu.shmonin.userservice.model.Role;
import edu.shmonin.userservice.model.User;
import edu.shmonin.userservice.repository.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oauthUser = super.loadUser(userRequest);
        var username = (String) oauthUser.getAttributes().get("login");
        if (userRepository.findByUsername(username).isEmpty()) {
            var user = new User();
            user.setUsername(username);
            var userRole = new Role();
            userRole.setName("ROLE_USER");
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }
        return oauthUser;
    }
}
