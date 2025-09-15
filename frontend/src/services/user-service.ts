import {apiClient} from './api-client';
import {User, UserPreferences} from '../types';

export class UserService {
  async getCurrentUser(): Promise<User> {
    return apiClient.get<User>('/users/me');
  }

  async updateUser(userData: Partial<User>): Promise<User> {
    return apiClient.put<User>('/users/me', userData);
  }

  async updatePreferences(preferences: UserPreferences): Promise<User> {
    return apiClient.put<User>('/users/me/preferences', preferences);
  }
}

export const userService = new UserService();
