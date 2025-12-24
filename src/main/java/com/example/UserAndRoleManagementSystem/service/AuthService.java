package com.example.UserAndRoleManagementSystem.service;

import com.example.UserAndRoleManagementSystem.dto.LoginRequest;
import com.example.UserAndRoleManagementSystem.entity.User;
import com.example.UserAndRoleManagementSystem.repository.UserRepository;
import com.example.UserAndRoleManagementSystem.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return JwtUtil.generateToken(user);
    }
}

