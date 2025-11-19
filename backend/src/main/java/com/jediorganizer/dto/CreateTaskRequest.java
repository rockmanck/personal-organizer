package com.jediorganizer.dto;

import com.jediorganizer.model.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for task creation requests.
 * Includes comprehensive validation annotations.
 */
@Schema(description = "Task creation request")
public class CreateTaskRequest {

    @NotBlank(message = "Task title is required")
    @Size(min = 1, max = 200, message = "Task title must be between 1 and 200 characters")
    @Schema(description = "Task title", example = "Review quarterly reports")
    private String title;

    @Size(max = 1000, message = "Task description cannot exceed 1000 characters")
    @Schema(description = "Task description", example = "Review and analyze Q3 financial reports")
    private String description;

    @NotNull(message = "Task type is required")
    @Schema(description = "Task type", example = "ACTION")
    private Task.TaskType type;

    @Size(max = 100, message = "Context cannot exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-_@]*$", message = "Context can only contain letters, numbers, spaces, hyphens, underscores, and @ symbol")
    @Schema(description = "Task context", example = "@office")
    private String context;

    @Min(value = 1, message = "Energy level must be between 1 and 5")
    @Max(value = 5, message = "Energy level must be between 1 and 5")
    @Schema(description = "Required energy level (1-5)", example = "3")
    private Integer energy;

    @Future(message = "Due date must be in the future")
    @Schema(description = "Task due date", example = "2025-10-01T12:00:00")
    private LocalDateTime dueDate;

    @Future(message = "Scheduled date must be in the future")
    @Schema(description = "Task scheduled date", example = "2025-09-20")
    private LocalDate scheduledDate;

    @Schema(description = "Project ID to associate task with", example = "project-123")
    private String projectId;

    // Constructors
    public CreateTaskRequest() {
        // Default constructor for JSON deserialization
    }

    public CreateTaskRequest(String title, String description, Task.TaskType type) {
        this.title = title;
        this.description = description;
        this.type = type;
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
     * Converts this DTO to a Task entity
     */
    public Task toTask() {
        Task task = new Task();
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setType(this.type != null ? this.type : Task.TaskType.ACTION);
        task.setContext(this.context);
        task.setEnergy(this.energy != null ? this.energy : 3);
        task.setDueDate(this.dueDate);
        task.setScheduledDate(this.scheduledDate);
        task.setProjectId(this.projectId);
        task.setStatus(Task.TaskStatus.TODO);
        task.setCreatedAt(LocalDateTime.now());
        return task;
    }
}