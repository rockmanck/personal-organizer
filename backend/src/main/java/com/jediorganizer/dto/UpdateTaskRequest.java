package com.jediorganizer.dto;

import com.jediorganizer.model.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for task update requests.
 * Includes comprehensive validation annotations.
 */
@Schema(description = "Task update request")
public class UpdateTaskRequest {

    @Size(min = 1, max = 200, message = "Task title must be between 1 and 200 characters")
    @Schema(description = "Task title", example = "Review quarterly reports")
    private String title;

    @Size(max = 1000, message = "Task description cannot exceed 1000 characters")
    @Schema(description = "Task description", example = "Review and analyze Q3 financial reports")
    private String description;

    @Schema(description = "Task type", example = "ACTION")
    private Task.TaskType type;

    @Schema(description = "Task status", example = "TODO")
    private Task.TaskStatus status;

    @Size(max = 100, message = "Context cannot exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-_@]*$", message = "Context can only contain letters, numbers, spaces, hyphens, underscores, and @ symbol")
    @Schema(description = "Task context", example = "@office")
    private String context;

    @Min(value = 1, message = "Energy level must be between 1 and 5")
    @Max(value = 5, message = "Energy level must be between 1 and 5")
    @Schema(description = "Required energy level (1-5)", example = "3")
    private Integer energy;

    @Schema(description = "Task due date", example = "2025-10-01T12:00:00")
    private LocalDateTime dueDate;

    @Schema(description = "Task scheduled date", example = "2025-09-20")
    private LocalDate scheduledDate;

    @Schema(description = "Project ID to associate task with", example = "project-123")
    private String projectId;

    // Constructors
    public UpdateTaskRequest() {
        // Default constructor for JSON deserialization
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task.TaskType getType() {
        return type;
    }

    public void setType(Task.TaskType type) {
        this.type = type;
    }

    public Task.TaskStatus getStatus() {
        return status;
    }

    public void setStatus(Task.TaskStatus status) {
        this.status = status;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * Updates the given task with non-null values from this DTO
     */
    public void updateTask(Task task) {
        if (this.title != null) {
            task.setTitle(this.title);
        }
        if (this.description != null) {
            task.setDescription(this.description);
        }
        if (this.type != null) {
            task.setType(this.type);
        }
        if (this.status != null) {
            task.setStatus(this.status);
        }
        if (this.context != null) {
            task.setContext(this.context);
        }
        if (this.energy != null) {
            task.setEnergy(this.energy);
        }
        if (this.dueDate != null) {
            task.setDueDate(this.dueDate);
        }
        if (this.scheduledDate != null) {
            task.setScheduledDate(this.scheduledDate);
        }
        if (this.projectId != null) {
            task.setProjectId(this.projectId);
        }
        task.setUpdatedAt(LocalDateTime.now());
    }
}