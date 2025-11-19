package com.jediorganizer.dto;

import com.jediorganizer.model.Project;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for project creation requests.
 * Includes comprehensive validation annotations.
 */
@Schema(description = "Project creation request")
public class CreateProjectRequest {

    @NotBlank(message = "Project title is required")
    @Size(min = 1, max = 200, message = "Project title must be between 1 and 200 characters")
    @Schema(description = "Project title", example = "Q4 Marketing Campaign")
    private String title;

    @Size(max = 2000, message = "Project description cannot exceed 2000 characters")
    @Schema(description = "Project description", example = "Plan and execute Q4 marketing campaign to increase brand awareness")
    private String description;

    @Future(message = "Due date must be in the future")
    @Schema(description = "Project due date", example = "2025-12-31T23:59:59")
    private LocalDateTime dueDate;

    @Schema(description = "Project status", example = "ACTIVE")
    private Project.ProjectStatus status;

    @Min(value = 1, message = "Priority must be between 1 and 5")
    @Max(value = 5, message = "Priority must be between 1 and 5")
    @Schema(description = "Project priority (1-5, where 1 is highest)", example = "3")
    private Integer priority;

    // Constructors
    public CreateProjectRequest() {
        // Default constructor for JSON deserialization
    }

    public CreateProjectRequest(String title, String description) {
        this.title = title;
        this.description = description;
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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Project.ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(Project.ProjectStatus status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * Converts this DTO to a Project entity
     */
    public Project toProject() {
        Project project = new Project();
        project.setTitle(this.title);
        project.setDescription(this.description);
        project.setDueDate(this.dueDate);
        project.setStatus(this.status != null ? this.status : Project.ProjectStatus.ACTIVE);
        
        if (this.priority != null) {
            project.setPriority(this.priority);
        }
        
        project.setCreatedAt(LocalDateTime.now());
        return project;
    }
}