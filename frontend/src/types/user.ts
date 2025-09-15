export interface UserPreferences {
  timezone: string;
  language: string;
  notificationsEnabled: boolean;
  emailNotifications: boolean;
  maxDailyTasks: number;
  reflectionReminderHour: number;
  weeklyReflectionEnabled: boolean;
}

export interface User {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  displayName: string;
  googleId?: string;
  profileImageUrl?: string;
  preferences: UserPreferences;
  active: boolean;
  createdAt: string;
  lastLoginAt?: string;
}
