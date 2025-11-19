package com.jediorganizer.service;

import com.jediorganizer.exception.BusinessValidationException;
import com.jediorganizer.exception.ResourceNotFoundException;
import com.jediorganizer.model.Task;
import com.jediorganizer.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Task entity business logic.
 * Handles task management operations implementing Jedi Techniques "Act" mode.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Create a new task
     */
    public Task createTask(Task task) {
        if (task.getUserId() == null) {
            throw new BusinessValidationException("MISSING_USER_ID", "Task must have a user ID");
        }
        return taskRepository.save(task);
    }

    /**
     * Find task by ID
     */
    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    /**
     * Find task by ID and verify user ownership
     */
    public Optional<Task> findByIdAndUserId(String id, String userId) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent() && task.get().getUserId().equals(userId)) {
            return task;
        }
        return Optional.empty();
    }

    /**
     * Get all tasks for a user
     */
    public List<Task> getUserTasks(String userId) {
        return taskRepository.findByUserId(userId);
    }

    /**
     * Get tasks by status
     */
    public List<Task> getTasksByStatus(String userId, Task.TaskStatus status) {
        return taskRepository.findByUserIdAndStatus(userId, status);
    }

    /**
     * Get today's tasks (core Jedi Techniques "Act" mode feature)
     */
    public List<Task> getTodaysTasks(String userId) {
        return taskRepository.findByUserIdAndScheduledDate(userId, LocalDate.now());
    }

    /**
     * Get today's actionable tasks (scheduled for today OR unscheduled active tasks)
     */
    public List<Task> getTodaysActionableTasks(String userId) {
        return taskRepository.findTodaysActionableTasks(userId, LocalDate.now());
    }

    /**
     * Get tasks for a specific project
     */
    public List<Task> getProjectTasks(String projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    /**
     * Get user's tasks for a specific project
     */
    public List<Task> getUserProjectTasks(String userId, String projectId) {
        return taskRepository.findByUserIdAndProjectId(userId, projectId);
    }

    /**
     * Get tasks without project assignment
     */
    public List<Task> getUnassignedTasks(String userId) {
        return taskRepository.findByUserIdAndProjectIdIsNull(userId);
    }

    /**
     * Get overdue tasks
     */
    public List<Task> getOverdueTasks(String userId) {
        return taskRepository.findByUserIdAndDueDateBeforeAndStatusNot(
            userId,
            LocalDateTime.now(),
            Task.TaskStatus.COMPLETED
        );
    }

    /**
     * Get tasks by context (e.g., "@computer", "@home")
     */
    public List<Task> getTasksByContext(String userId, String context) {
        return taskRepository.findByUserIdAndContext(userId, context);
    }

    /**
     * Get tasks by energy level (for matching current energy)
     */
    public List<Task> getTasksByEnergyLevel(String userId, int maxEnergy) {
        return taskRepository.findByUserIdAndEnergyLessThanEqual(userId, maxEnergy);
    }

    /**
     * Update task
     */
    public Task updateTask(Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Task ID cannot be null for update");
        }
        return taskRepository.save(task);
    }

    /**
     * Start a task (change to IN_PROGRESS)
     */
    public Task startTask(String taskId, String userId) {
        Optional<Task> taskOpt = findByIdAndUserId(taskId, userId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.start();
            return taskRepository.save(task);
        }
        throw new ResourceNotFoundException("Task", taskId);
    }

    /**
     * Complete a task
     */
    public Task completeTask(String taskId, String userId) {
        Optional<Task> taskOpt = findByIdAndUserId(taskId, userId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.complete();
            return taskRepository.save(task);
        }
        throw new ResourceNotFoundException("Task", taskId);
    }

    /**
     * Schedule task for a specific date
     */
    public Task scheduleTask(String taskId, String userId, LocalDate scheduledDate) {
        Optional<Task> taskOpt = findByIdAndUserId(taskId, userId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setScheduledDate(scheduledDate);
            return taskRepository.save(task);
        }
        throw new IllegalArgumentException("Task not found or access denied");
    }

    /**
     * Add note to task
     */
    public Task addTaskNote(String taskId, String userId, String noteContent) {
        Optional<Task> taskOpt = findByIdAndUserId(taskId, userId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.addNote(noteContent);
            return taskRepository.save(task);
        }
        throw new IllegalArgumentException("Task not found or access denied");
    }

    /**
     * Add reflection to completed task
     */
    public Task addTaskReflection(String taskId, String userId, Task.TaskReflection reflection) {
        Optional<Task> taskOpt = findByIdAndUserId(taskId, userId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            if (task.getStatus() != Task.TaskStatus.COMPLETED) {
                throw new IllegalArgumentException("Can only add reflection to completed tasks");
            }
            task.setReflection(reflection);
            return taskRepository.save(task);
        }
        throw new IllegalArgumentException("Task not found or access denied");
    }

    /**
     * Search tasks by title
     */
    public List<Task> searchTasksByTitle(String userId, String searchTerm) {
        return taskRepository.findByUserIdAndTitleContainingIgnoreCase(userId, searchTerm);
    }

    /**
     * Get completed tasks for reflection (within date range)
     */
    public List<Task> getCompletedTasksForReflection(String userId, LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.findByUserIdAndStatusAndCompletedAtBetween(
            userId, Task.TaskStatus.COMPLETED, startDate, endDate);
    }

    /**
     * Get tasks with reflection data
     */
    public List<Task> getTasksWithReflection(String userId) {
        return taskRepository.findByUserIdAndReflectionIsNotNull(userId);
    }

    /**
     * Get task statistics for a user
     */
    public TaskStatistics getTaskStatistics(String userId) {
        long totalTasks = taskRepository.findByUserId(userId).size();
        long todoTasks = taskRepository.countByUserIdAndStatus(userId, Task.TaskStatus.TODO);
        long inProgressTasks = taskRepository.countByUserIdAndStatus(userId, Task.TaskStatus.IN_PROGRESS);
        long completedTasks = taskRepository.countByUserIdAndStatus(userId, Task.TaskStatus.COMPLETED);
        long waitingTasks = taskRepository.countByUserIdAndStatus(userId, Task.TaskStatus.WAITING);

        List<Task> todaysTasks = getTodaysTasks(userId);
        List<Task> overdueTasks = getOverdueTasks(userId);

        return new TaskStatistics(
            totalTasks, todoTasks, inProgressTasks, completedTasks, waitingTasks,
            todaysTasks.size(), overdueTasks.size()
        );
    }

    /**
     * Delete task (use with caution)
     */
    public void deleteTask(String taskId, String userId) {
        Optional<Task> task = findByIdAndUserId(taskId, userId);
        if (task.isPresent()) {
            taskRepository.deleteById(taskId);
        } else {
            throw new IllegalArgumentException("Task not found or access denied");
        }
    }

    /**
     * Task statistics data class
     */
    public static class TaskStatistics {
        private final long totalTasks;
        private final long todoTasks;
        private final long inProgressTasks;
        private final long completedTasks;
        private final long waitingTasks;
        private final long todaysTasks;
        private final long overdueTasks;

        public TaskStatistics(long totalTasks, long todoTasks, long inProgressTasks,
                            long completedTasks, long waitingTasks, long todaysTasks,
                            long overdueTasks) {
            this.totalTasks = totalTasks;
            this.todoTasks = todoTasks;
            this.inProgressTasks = inProgressTasks;
            this.completedTasks = completedTasks;
            this.waitingTasks = waitingTasks;
            this.todaysTasks = todaysTasks;
            this.overdueTasks = overdueTasks;
        }

        // Getters
        public long getTotalTasks() { return totalTasks; }
        public long getTodoTasks() { return todoTasks; }
        public long getInProgressTasks() { return inProgressTasks; }
        public long getCompletedTasks() { return completedTasks; }
        public long getWaitingTasks() { return waitingTasks; }
        public long getTodaysTasks() { return todaysTasks; }
        public long getOverdueTasks() { return overdueTasks; }
    }
}
