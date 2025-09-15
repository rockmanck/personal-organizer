package com.jediorganizer.controller;

import com.jediorganizer.model.User;
import com.jediorganizer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for User management operations.
 * Provides endpoints for user CRUD operations.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management operations")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get current user profile (placeholder for authenticated user)
     */
    @GetMapping("/me")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<User> getCurrentUser() {
        // TODO: Get user ID from authentication context
        // For now, return a demo user
        User demoUser = new User("demo@example.com", "Demo", "User");
        demoUser.setId("demo-user-123");
        return ResponseEntity.ok(demoUser);
    }

    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new user
     */
    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update user profile
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update user profile")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            user.setId(id);
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all active users (admin function)
     */
    @GetMapping
    @Operation(summary = "Get all active users")
    public ResponseEntity<List<User>> getActiveUsers() {
        List<User> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Deactivate user account
     */
    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate user account")
    public ResponseEntity<User> deactivateUser(@PathVariable String id) {
        try {
            User user = userService.deactivateUser(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Check if email exists
     */
    @GetMapping("/check-email")
    @Operation(summary = "Check if email exists")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    /**
     * Get user statistics
     */
    @GetMapping("/stats")
    @Operation(summary = "Get user statistics")
    public ResponseEntity<UserStats> getUserStats() {
        long totalUsers = userService.getTotalUserCount();
        long activeUsers = userService.getActiveUserCount();

        return ResponseEntity.ok(new UserStats(totalUsers, activeUsers));
    }

    /**
     * User statistics response class
     */
    public static class UserStats {
        private final long totalUsers;
        private final long activeUsers;

        public UserStats(long totalUsers, long activeUsers) {
            this.totalUsers = totalUsers;
            this.activeUsers = activeUsers;
        }

        public long getTotalUsers() { return totalUsers; }
        public long getActiveUsers() { return activeUsers; }
    }
}
