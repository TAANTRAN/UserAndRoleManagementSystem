package com.example.UserAndRoleManagementSystem.service;

import com.example.UserAndRoleManagementSystem.dto.CreateUserRequest;
import com.example.UserAndRoleManagementSystem.dto.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);
    UserResponse getUserByUsername(String username);
}
