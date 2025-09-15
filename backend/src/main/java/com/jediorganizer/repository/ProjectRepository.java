package com.jediorganizer.repository;

import com.jediorganizer.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Project entity operations.
 * Provides MongoDB data access for project management.
 */
@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    
    /**
     * Find all projects for a specific user
     */
    List<Project> findByUserId(String userId);
    
    /**
     * Find projects by user and status
     */
    List<Project> findByUserIdAndStatus(String userId, Project.ProjectStatus status);
    
    /**
     * Find active projects for a user
     */
    List<Project> findByUserIdAndStatusNot(String userId, Project.ProjectStatus status);
    
    /**
     * Find projects by priority level
     */
    List<Project> findByUserIdAndPriorityLessThanEqual(String userId, int priority);
    
    /**
     * Find overdue projects
     */
    List<Project> findByUserIdAndDueDateBeforeAndStatusNot(
        String userId, 
        LocalDateTime date, 
        Project.ProjectStatus status
    );
    
    /**
     * Find projects created after specified date
     */
    List<Project> findByUserIdAndCreatedAtAfter(String userId, LocalDateTime date);
    
    /**
     * Find projects updated within specified timeframe
     */
    List<Project> findByUserIdAndUpdatedAtAfter(String userId, LocalDateTime date);
    
    /**
     * Count active projects for a user
     */
    long countByUserIdAndStatus(String userId, Project.ProjectStatus status);
    
    /**
     * Find projects by title containing text (case-insensitive search)
     */
    List<Project> findByUserIdAndTitleContainingIgnoreCase(String userId, String title);
}
