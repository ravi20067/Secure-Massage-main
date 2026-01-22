package com.Security.Secure.Massage.Controller;

import com.Security.Secure.Massage.Entity.User;
import com.Security.Secure.Massage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(Authentication authentication) {

        String currentUserEmail = authentication.getName();

        // return all users except logged-in user
        return userRepository.findAll()
                .stream()
                .filter(u -> !u.getEmail().equals(currentUserEmail))
                .toList();
    }
}

