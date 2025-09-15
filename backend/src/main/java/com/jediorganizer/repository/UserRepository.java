package com.jediorganizer.repository;

import com.jediorganizer.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository interface for User entity operations.
 * Provides MongoDB data access for user management.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    /**
     * Find user by email address (unique identifier)
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find user by Google OAuth ID
     */
    Optional<User> findByGoogleId(String googleId);
    
    /**
     * Check if user exists by email
     */
    boolean existsByEmail(String email);
    
    /**
     * Find all active users
     */
    java.util.List<User> findByActiveTrue();
    
    /**
     * Find users who haven't logged in since specified date
     */
    java.util.List<User> findByLastLoginAtBefore(LocalDateTime date);
    
    /**
     * Find users created after specified date
     */
    java.util.List<User> findByCreatedAtAfter(LocalDateTime date);
}
