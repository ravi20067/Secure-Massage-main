package com.Security.Secure.Massage.Service;

import com.Security.Secure.Massage.Entity.User;
import com.Security.Secure.Massage.Enums.AuthType;
import com.Security.Secure.Massage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

    @Component
    public class UserService {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder ;

    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        user.setAuthType(AuthType.PASSWORD);
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Boolean isPresent(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public void save(User user){
        userRepository.save(user);
    }
    public void updatePassword(String email, String rawPassword) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
    }
    public User getCurrentUser(Authentication authentication) {

            // 1️⃣ Safety check
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

            // 2️⃣ Get username/email from Spring Security
        String email = authentication.getName();

            // 3️⃣ Fetch user from MongoDB
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found with email: " + email)
                );
        }
}
