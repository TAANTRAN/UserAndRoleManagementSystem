package com.example.UserAndRoleManagementSystem.config;

import com.example.UserAndRoleManagementSystem.entity.Role;
import com.example.UserAndRoleManagementSystem.entity.User;
import com.example.UserAndRoleManagementSystem.repository.RoleRepository;
import com.example.UserAndRoleManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataTestRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner testData(PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByUsername("tan").isPresent()) {
                System.out.println("User 'tan' already exists, skip insert");
                return;
            }

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

            User user = new User();
            user.setUsername("tan");
            user.setEmail("tan@test.com");
            //user.setPassword("123456");
            user.setPassword(passwordEncoder.encode("123456")); // Hash the password
            user.setStatus("ACTIVE");
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setRoles(Set.of(userRole));

            userRepository.save(user);

            System.out.println("Saved user: " + user.getUsername());
            System.out.println("User roles: " + user.getRoles());
        };
    }
}
