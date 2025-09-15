package com.jediorganizer.repository;

import com.jediorganizer.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Task entity operations.
 * Provides MongoDB data access for task management in Jedi Techniques.
 */
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    /**
     * Find all tasks for a specific user
     */
    List<Task> findByUserId(String userId);

    /**
     * Find tasks by user and status
     */
    List<Task> findByUserIdAndStatus(String userId, Task.TaskStatus status);

    /**
     * Find tasks by project
     */
    List<Task> findByProjectId(String projectId);

    /**
     * Find tasks by user and project
     */
    List<Task> findByUserIdAndProjectId(String userId, String projectId);

    /**
     * Find tasks scheduled for a specific date
     */
    List<Task> findByUserIdAndScheduledDate(String userId, LocalDate scheduledDate);

    /**
     * Find tasks scheduled for today
     */
    @Query("{ 'userId': ?0, 'scheduledDate': ?1 }")
    List<Task> findByUserIdAndScheduledDateToday(String userId, LocalDate today);

    /**
     * Find overdue tasks
     */
    List<Task> findByUserIdAndDueDateBeforeAndStatusNot(
        String userId,
        LocalDateTime date,
        Task.TaskStatus status
    );

    /**
     * Find tasks by status and scheduled date range
     */
    List<Task> findByUserIdAndStatusAndScheduledDateBetween(
        String userId,
        Task.TaskStatus status,
        LocalDate startDate,
        LocalDate endDate
    );

    /**
     * Find tasks by context
     */
    List<Task> findByUserIdAndContext(String userId, String context);

    /**
     * Find tasks by energy level
     */
    List<Task> findByUserIdAndEnergyLessThanEqual(String userId, int maxEnergy);

    /**
     * Find completed tasks within date range for reflection
     */
    List<Task> findByUserIdAndStatusAndCompletedAtBetween(
        String userId,
        Task.TaskStatus status,
        LocalDateTime startDate,
        LocalDateTime endDate
    );

    /**
     * Find tasks without project assignment
     */
    List<Task> findByUserIdAndProjectIdIsNull(String userId);

    /**
     * Find tasks with reflection data
     */
    List<Task> findByUserIdAndReflectionIsNotNull(String userId);

    /**
     * Count tasks by status for a user
     */
    long countByUserIdAndStatus(String userId, Task.TaskStatus status);

    /**
     * Find tasks by title containing text (case-insensitive search)
     */
    List<Task> findByUserIdAndTitleContainingIgnoreCase(String userId, String title);

    /**
     * Find tasks created after specified date
     */
    List<Task> findByUserIdAndCreatedAtAfter(String userId, LocalDateTime date);

    /**
     * Find tasks updated recently
     */
    List<Task> findByUserIdAndUpdatedAtAfter(String userId, LocalDateTime date);

    /**
     * Find tasks by type
     */
    List<Task> findByUserIdAndType(String userId, Task.TaskType type);

    /**
     * Custom query to find today's actionable tasks
     * (scheduled for today OR high priority with no scheduled date)
     */
    @Query("{ 'userId': ?0, $or: [ " +
           "{ 'scheduledDate': ?1 }, " +
           "{ 'scheduledDate': null, 'priority': { $in: ['URGENT', 'HIGH'] }, 'status': { $in: ['TODO', 'IN_PROGRESS'] } } " +
           "] }")
    List<Task> findTodaysActionableTasks(String userId, LocalDate today);
}
