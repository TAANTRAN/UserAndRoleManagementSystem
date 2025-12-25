package com.example.UserAndRoleManagementSystem.controller;

import com.example.UserAndRoleManagementSystem.dto.UserResponse;
import com.example.UserAndRoleManagementSystem.dto.response.ApiResponse;
import com.example.UserAndRoleManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity getUser(@PathVariable String username) {
        return ResponseEntity.ok(
                ApiResponse.success(userService.getUserByUsername(username))
        );
    }
}
