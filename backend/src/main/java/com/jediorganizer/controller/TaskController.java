package com.jediorganizer.controller;

import com.jediorganizer.model.Task;
import com.jediorganizer.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Task management operations.
 * Implements Jedi Techniques "Act" mode functionality.
 */
@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Task management for Jedi Techniques Act mode")
public class TaskController {

    private final TaskService taskService;
    private static final String DEMO_USER_ID = "demo-user-123";

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "Get all user tasks")
    public ResponseEntity<List<Task>> getUserTasks() {
        List<Task> tasks = taskService.getUserTasks(DEMO_USER_ID);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get today's actionable tasks (core Jedi Techniques feature)
     */
    @GetMapping("/today")
    @Operation(summary = "Get today's actionable tasks")
    public ResponseEntity<List<Task>> getTodaysActionableTasks() {
        List<Task> tasks = taskService.getTodaysActionableTasks(DEMO_USER_ID);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get tasks by status")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable Task.TaskStatus status) {
        List<Task> tasks = taskService.getTasksByStatus(DEMO_USER_ID, status);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get overdue tasks
     */
    @GetMapping("/overdue")
    @Operation(summary = "Get overdue tasks")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        List<Task> tasks = taskService.getOverdueTasks(DEMO_USER_ID);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks by context
     */
    @GetMapping("/context/{context}")
    @Operation(summary = "Get tasks by context")
    public ResponseEntity<List<Task>> getTasksByContext(@PathVariable String context) {
        List<Task> tasks = taskService.getTasksByContext(DEMO_USER_ID, context);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks by energy level
     */
    @GetMapping("/energy/{maxEnergy}")
    @Operation(summary = "Get tasks by maximum energy level")
    public ResponseEntity<List<Task>> getTasksByEnergyLevel(@PathVariable int maxEnergy) {
        List<Task> tasks = taskService.getTasksByEnergyLevel(DEMO_USER_ID, maxEnergy);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get task by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Optional<Task> task = taskService.findByIdAndUserId(id, DEMO_USER_ID);
        return task.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new task
     */
    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            task.setUserId(DEMO_USER_ID);
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update task
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update task")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task task) {
        try {
            // Verify task exists and belongs to user
            Optional<Task> existingTask = taskService.findByIdAndUserId(id, DEMO_USER_ID);
            if (existingTask.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            task.setId(id);
            task.setUserId(DEMO_USER_ID);
            Task updatedTask = taskService.updateTask(task);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Start a task (change status to IN_PROGRESS)
     */
    @PatchMapping("/{id}/start")
    @Operation(summary = "Start a task")
    public ResponseEntity<Task> startTask(@PathVariable String id) {
        try {
            Task task = taskService.startTask(id, DEMO_USER_ID);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Complete a task")
    public ResponseEntity<Task> completeTask(@PathVariable String id) {
        try {
            Task task = taskService.completeTask(id, DEMO_USER_ID);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Schedule task for a specific date
     */
    @PatchMapping("/{id}/schedule")
    @Operation(summary = "Schedule task for a specific date")
    public ResponseEntity<Task> scheduleTask(@PathVariable String id,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate scheduledDate) {
        try {
            Task task = taskService.scheduleTask(id, DEMO_USER_ID, scheduledDate);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add note to task
     */
    @PostMapping("/{id}/notes")
    @Operation(summary = "Add note to task")
    public ResponseEntity<Task> addTaskNote(@PathVariable String id, @RequestBody String noteContent) {
        try {
            Task task = taskService.addTaskNote(id, DEMO_USER_ID, noteContent);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add reflection to completed task
     */
    @PostMapping("/{id}/reflection")
    @Operation(summary = "Add reflection to completed task")
    public ResponseEntity<Task> addTaskReflection(@PathVariable String id,
                                                @RequestBody Task.TaskReflection reflection) {
        try {
            Task task = taskService.addTaskReflection(id, DEMO_USER_ID, reflection);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Search tasks by title
     */
    @GetMapping("/search")
    @Operation(summary = "Search tasks by title")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String query) {
        List<Task> tasks = taskService.searchTasksByTitle(DEMO_USER_ID, query);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get task statistics
     */
    @GetMapping("/stats")
    @Operation(summary = "Get task statistics")
    public ResponseEntity<TaskService.TaskStatistics> getTaskStatistics() {
        TaskService.TaskStatistics stats = taskService.getTaskStatistics(DEMO_USER_ID);
        return ResponseEntity.ok(stats);
    }

    /**
     * Get tasks with reflection data (for Reflect mode)
     */
    @GetMapping("/with-reflection")
    @Operation(summary = "Get tasks with reflection data")
    public ResponseEntity<List<Task>> getTasksWithReflection() {
        List<Task> tasks = taskService.getTasksWithReflection(DEMO_USER_ID);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Delete task
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(id, DEMO_USER_ID);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
