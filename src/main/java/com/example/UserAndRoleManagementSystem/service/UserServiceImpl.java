package com.example.UserAndRoleManagementSystem.service;

import com.example.UserAndRoleManagementSystem.dto.CreateUserRequest;
import com.example.UserAndRoleManagementSystem.dto.UserResponse;
import com.example.UserAndRoleManagementSystem.entity.Role;
import com.example.UserAndRoleManagementSystem.entity.User;
import com.example.UserAndRoleManagementSystem.exception.ResourceNotFoundException;
import com.example.UserAndRoleManagementSystem.repository.RoleRepository;
import com.example.UserAndRoleManagementSystem.repository.UserRepository;
import com.example.UserAndRoleManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));


        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        //user.setPassword(request.getPassword()); // tạm, chưa hash
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRoles(Set.of(userRole));

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getStatus(),
                savedUser.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public UserResponse getUserByUsername(String username) {

        //User user = userRepository.findByUsername(username)
        //        .orElseThrow(() -> new RuntimeException("User not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus(),
                user.getRoles()
                        .stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toSet())
        );
    }
}
