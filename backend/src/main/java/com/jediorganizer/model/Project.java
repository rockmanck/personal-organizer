package com.jediorganizer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Project entity representing a Jedi Organizer project.
 * Projects group related tasks and implement the "Plan" mode of Jedi Techniques.
 */
@Document(collection = "projects")
public class Project {

    @Id
    private String id;

    private String title;
    private String description;

    // Owner of the project
    private String userId;

    // Project status in Jedi Techniques workflow
    private ProjectStatus status = ProjectStatus.ACTIVE;

    // Project priority (1-5, where 1 is highest)
    private int priority = 3;

    // Project metadata
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
    private LocalDateTime completedAt;

    // Project settings
    private ProjectSettings settings;

    public Project() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.settings = new ProjectSettings();
    }

    // Constructor with essential fields
    public Project(String title, String userId) {
        this();
        this.title = title;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();

        if (status == ProjectStatus.COMPLETED && this.completedAt == null) {
            this.completedAt = LocalDateTime.now();
        }
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }
        this.priority = priority;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public ProjectSettings getSettings() {
        return settings;
    }

    public void setSettings(ProjectSettings settings) {
        this.settings = settings;
    }

    // Business methods
    public void markCompleted() {
        setStatus(ProjectStatus.COMPLETED);
    }

    public void archive() {
        setStatus(ProjectStatus.ARCHIVED);
    }

    public boolean isOverdue() {
        return dueDate != null && LocalDateTime.now().isAfter(dueDate) && status != ProjectStatus.COMPLETED;
    }

    public boolean isHighPriority() {
        return priority <= 2;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                '}';
    }

    /**
     * Project status enum for Jedi Techniques workflow
     */
    public enum ProjectStatus {
        ACTIVE,      // Project is actively being worked on
        ON_HOLD,     // Project is temporarily paused
        COMPLETED,   // Project is finished
        ARCHIVED     // Project is archived (not deleted, for reflection)
    }

    /**
     * Project settings for customization
     */
    public static class ProjectSettings {
        private boolean notificationsEnabled = true;
        private boolean autoArchiveWhenComplete = false;

        // Jedi Techniques specific settings
        private int maxDailyTasksFromProject = 5;
        private boolean includeInWeeklyReflection = true;

        public ProjectSettings() {
            // Initialize with sensible defaults for Jedi Techniques methodology
        }

        public boolean isNotificationsEnabled() {
            return notificationsEnabled;
        }

        public void setNotificationsEnabled(boolean notificationsEnabled) {
            this.notificationsEnabled = notificationsEnabled;
        }

        public boolean isAutoArchiveWhenComplete() {
            return autoArchiveWhenComplete;
        }

        public void setAutoArchiveWhenComplete(boolean autoArchiveWhenComplete) {
            this.autoArchiveWhenComplete = autoArchiveWhenComplete;
        }

        public int getMaxDailyTasksFromProject() {
            return maxDailyTasksFromProject;
        }

        public void setMaxDailyTasksFromProject(int maxDailyTasksFromProject) {
            this.maxDailyTasksFromProject = maxDailyTasksFromProject;
        }

        public boolean isIncludeInWeeklyReflection() {
            return includeInWeeklyReflection;
        }

        public void setIncludeInWeeklyReflection(boolean includeInWeeklyReflection) {
            this.includeInWeeklyReflection = includeInWeeklyReflection;
        }
    }
}
