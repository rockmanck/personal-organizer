package com.jediorganizer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * User entity representing a Jedi Organizer user.
 * Implements the core user model for the Jedi Techniques productivity system.
 */
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String firstName;
    private String lastName;
    private String displayName;

    // OAuth2 integration
    private String googleId;
    private String profileImageUrl;

    private UserPreferences preferences;

    // Account status
    private boolean active = true;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public User() {
        this.createdAt = LocalDateTime.now();
        this.preferences = new UserPreferences();
    }

    public User(String email, String firstName, String lastName) {
        this();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = firstName + " " + lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public UserPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPreferences preferences) {
        this.preferences = preferences;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    // Business methods
    public void updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", active=" + active +
                '}';
    }

    /**
     * User preferences for Jedi Techniques methodology
     */
    public static class UserPreferences {
        private String timezone = "UTC";
        private String language = "en";
        private boolean notificationsEnabled = true;
        private boolean emailNotifications = true;

        private int maxDailyTasks = 10;
        private int reflectionReminderHour = 18; // 6 PM
        private boolean weeklyReflectionEnabled = true;

        public UserPreferences() {}

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public boolean isNotificationsEnabled() {
            return notificationsEnabled;
        }

        public void setNotificationsEnabled(boolean notificationsEnabled) {
            this.notificationsEnabled = notificationsEnabled;
        }

        public boolean isEmailNotifications() {
            return emailNotifications;
        }

        public void setEmailNotifications(boolean emailNotifications) {
            this.emailNotifications = emailNotifications;
        }

        public int getMaxDailyTasks() {
            return maxDailyTasks;
        }

        public void setMaxDailyTasks(int maxDailyTasks) {
            this.maxDailyTasks = maxDailyTasks;
        }

        public int getReflectionReminderHour() {
            return reflectionReminderHour;
        }

        public void setReflectionReminderHour(int reflectionReminderHour) {
            this.reflectionReminderHour = reflectionReminderHour;
        }

        public boolean isWeeklyReflectionEnabled() {
            return weeklyReflectionEnabled;
        }

        public void setWeeklyReflectionEnabled(boolean weeklyReflectionEnabled) {
            this.weeklyReflectionEnabled = weeklyReflectionEnabled;
        }
    }
}
