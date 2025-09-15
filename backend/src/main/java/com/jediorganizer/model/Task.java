package com.jediorganizer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Task entity representing a Jedi Organizer task.
 * Implements the core "Act" mode of Jedi Techniques methodology.
 */
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    private String title;
    private String description;

    // Ownership
    private String userId;
    private String projectId; // Optional: task can exist without project

    // Jedi Techniques core properties
    private TaskStatus status = TaskStatus.TODO;
    private TaskType type = TaskType.ACTION;

    // Time management
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate scheduledDate; // When task should be worked on
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime dueDate;

    // Jedi Techniques specific fields
    private String context; // Context for task execution (e.g., "@computer", "@home")
    private int energy = 3; // Energy level required (1-5)

    // Task workflow
    private List<String> subtasks = new ArrayList<>();
    private List<TaskNote> notes = new ArrayList<>();

    // Reflection data
    private TaskReflection reflection;

    public Task() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Constructor with essential fields
    public Task(String title, String userId) {
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();

        if (status == TaskStatus.IN_PROGRESS && this.startedAt == null) {
            this.startedAt = LocalDateTime.now();
        } else if (status == TaskStatus.COMPLETED && this.completedAt == null) {
            this.completedAt = LocalDateTime.now();
        }
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
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

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        this.updatedAt = LocalDateTime.now();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        if (energy < 1 || energy > 5) {
            throw new IllegalArgumentException("Energy level must be between 1 and 5");
        }
        this.energy = energy;
    }

    public List<String> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<String> subtasks) {
        this.subtasks = subtasks != null ? subtasks : new ArrayList<>();
    }

    public List<TaskNote> getNotes() {
        return notes;
    }

    public void setNotes(List<TaskNote> notes) {
        this.notes = notes != null ? notes : new ArrayList<>();
    }

    public TaskReflection getReflection() {
        return reflection;
    }

    public void setReflection(TaskReflection reflection) {
        this.reflection = reflection;
    }

    // Business methods
    public void start() {
        setStatus(TaskStatus.IN_PROGRESS);
    }

    public void complete() {
        setStatus(TaskStatus.COMPLETED);
    }

    public void addNote(String content) {
        if (this.notes == null) {
            this.notes = new ArrayList<>();
        }
        this.notes.add(new TaskNote(content));
        this.updatedAt = LocalDateTime.now();
    }

    public void addSubtask(String subtask) {
        if (this.subtasks == null) {
            this.subtasks = new ArrayList<>();
        }
        this.subtasks.add(subtask);
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isOverdue() {
        return dueDate != null && LocalDateTime.now().isAfter(dueDate) && status != TaskStatus.COMPLETED;
    }

    public boolean isScheduledForToday() {
        return scheduledDate != null && scheduledDate.equals(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }

    /**
     * Task status enum for Jedi Techniques workflow
     */
    public enum TaskStatus {
        TODO,           // Task is planned but not started
        IN_PROGRESS,    // Task is currently being worked on
        WAITING,        // Task is waiting for external dependency
        COMPLETED,      // Task is finished
        CANCELLED       // Task was cancelled
    }

    /**
     * Task type enum for different kinds of work
     */
    public enum TaskType {
        ACTION,         // Concrete actionable task
        WAITING_FOR,    // Waiting for someone else
        REFERENCE,      // Reference material
        SOMEDAY_MAYBE   // Ideas for future consideration
    }

    /**
     * Task note for additional information
     */
    public static class TaskNote {
        private String content;
        private LocalDateTime createdAt;

        public TaskNote() {
            this.createdAt = LocalDateTime.now();
        }

        public TaskNote(String content) {
            this();
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }
    }

    /**
     * Task reflection for the "Reflect" mode
     */
    public static class TaskReflection {
        private String whatWentWell;
        private String whatCouldImprove;
        private String lessonsLearned;
        private int satisfactionRating; // 1-5 scale
        private LocalDateTime reflectedAt;

        public TaskReflection() {
            this.reflectedAt = LocalDateTime.now();
        }

        public String getWhatWentWell() {
            return whatWentWell;
        }

        public void setWhatWentWell(String whatWentWell) {
            this.whatWentWell = whatWentWell;
        }

        public String getWhatCouldImprove() {
            return whatCouldImprove;
        }

        public void setWhatCouldImprove(String whatCouldImprove) {
            this.whatCouldImprove = whatCouldImprove;
        }

        public String getLessonsLearned() {
            return lessonsLearned;
        }

        public void setLessonsLearned(String lessonsLearned) {
            this.lessonsLearned = lessonsLearned;
        }

        public int getSatisfactionRating() {
            return satisfactionRating;
        }

        public void setSatisfactionRating(int satisfactionRating) {
            if (satisfactionRating < 1 || satisfactionRating > 5) {
                throw new IllegalArgumentException("Satisfaction rating must be between 1 and 5");
            }
            this.satisfactionRating = satisfactionRating;
        }

        public LocalDateTime getReflectedAt() {
            return reflectedAt;
        }

        public void setReflectedAt(LocalDateTime reflectedAt) {
            this.reflectedAt = reflectedAt;
        }
    }
}
