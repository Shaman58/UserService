package edu.shmonin.userservice.controller;

import edu.shmonin.userservice.model.User;
import edu.shmonin.userservice.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<User> getTestString() {
        return userRepository.findAll();
    }
}
