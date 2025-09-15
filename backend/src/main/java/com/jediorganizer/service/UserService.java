package com.jediorganizer.service;

import com.jediorganizer.model.User;
import com.jediorganizer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for User entity business logic.
 * Handles user management operations for Jedi Organizer.
 */
@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Create a new user
     */
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }
    
    /**
     * Find user by ID
     */
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
    
    /**
     * Find user by email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Find user by Google ID
     */
    public Optional<User> findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }
    
    /**
     * Update user information
     */
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null for update");
        }
        return userRepository.save(user);
    }
    
    /**
     * Update user's last login time
     */
    public User updateLastLogin(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.updateLastLogin();
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("User not found with ID: " + userId);
    }
    
    /**
     * Deactivate user (soft delete)
     */
    public User deactivateUser(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setActive(false);
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("User not found with ID: " + userId);
    }
    
    /**
     * Reactivate user
     */
    public User reactivateUser(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setActive(true);
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("User not found with ID: " + userId);
    }
    
    /**
     * Get all active users
     */
    public List<User> getActiveUsers() {
        return userRepository.findByActiveTrue();
    }
    
    /**
     * Check if user exists by email
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    /**
     * Get inactive users (for cleanup operations)
     */
    public List<User> getInactiveUsers(int daysThreshold) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysThreshold);
        return userRepository.findByLastLoginAtBefore(cutoffDate);
    }
    
    /**
     * Get new users (registered within specified days)
     */
    public List<User> getNewUsers(int daysBack) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysBack);
        return userRepository.findByCreatedAtAfter(cutoffDate);
    }
    
    /**
     * Get total user count
     */
    public long getTotalUserCount() {
        return userRepository.count();
    }
    
    /**
     * Get active user count
     */
    public long getActiveUserCount() {
        return userRepository.findByActiveTrue().size();
    }
    
    /**
     * Create or update user from OAuth provider (Google)
     */
    public User createOrUpdateFromOAuth(String email, String firstName, String lastName, 
                                       String googleId, String profileImageUrl) {
        // Try to find existing user by Google ID first
        Optional<User> existingUser = userRepository.findByGoogleId(googleId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            // Update user info from OAuth
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setDisplayName(firstName + " " + lastName);
            user.setProfileImageUrl(profileImageUrl);
            user.updateLastLogin();
            return userRepository.save(user);
        }
        
        // Try to find by email (existing user linking OAuth)
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            // Link Google OAuth to existing account
            user.setGoogleId(googleId);
            user.setProfileImageUrl(profileImageUrl);
            user.updateLastLogin();
            return userRepository.save(user);
        }
        
        // Create new user
        User newUser = new User(email, firstName, lastName);
        newUser.setGoogleId(googleId);
        newUser.setProfileImageUrl(profileImageUrl);
        newUser.updateLastLogin();
        return userRepository.save(newUser);
    }
    
    /**
     * Delete user permanently (use with caution)
     */
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
