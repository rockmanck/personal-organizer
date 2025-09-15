import {apiClient} from './api-client';
import {CreateTaskRequest, Task, TaskStatus, UpdateTaskRequest} from '../types';

export interface TaskFilters {
  status?: TaskStatus;
  projectId?: string;
  scheduledFor?: string;
  tags?: string[];
}

export class TaskService {
  async getTasks(filters?: TaskFilters): Promise<Task[]> {
    const queryParams = new URLSearchParams();
    if (filters) {
      Object.entries(filters).forEach(([key, value]) => {
        if (value !== undefined) {
          if (Array.isArray(value)) {
            value.forEach(v => queryParams.append(key, v));
          } else {
            queryParams.append(key, value.toString());
          }
        }
      });
    }
    const queryString = queryParams.toString();
    const endpoint = queryString ? `/tasks?${queryString}` : '/tasks';
    return apiClient.get<Task[]>(endpoint);
  }

  async getTodayTasks(): Promise<Task[]> {
    return apiClient.get<Task[]>('/tasks/today');
  }

  async getActionableTasks(): Promise<Task[]> {
    return apiClient.get<Task[]>('/tasks/actionable');
  }

  async getTask(id: string): Promise<Task> {
    return apiClient.get<Task>(`/tasks/${id}`);
  }

  async createTask(taskData: CreateTaskRequest): Promise<Task> {
    return apiClient.post<Task>('/tasks', taskData);
  }

  async updateTask(id: string, taskData: UpdateTaskRequest): Promise<Task> {
    return apiClient.put<Task>(`/tasks/${id}`, taskData);
  }

  async deleteTask(id: string): Promise<void> {
    return apiClient.delete<void>(`/tasks/${id}`);
  }

  async completeTask(id: string): Promise<Task> {
    return apiClient.patch<Task>(`/tasks/${id}/complete`);
  }

  async reorderTask(id: string, newOrder: number): Promise<Task> {
    return apiClient.patch<Task>(`/tasks/${id}/reorder`, { userDefinedOrder: newOrder });
  }

  async addTaskNote(id: string, content: string): Promise<Task> {
    return apiClient.post<Task>(`/tasks/${id}/notes`, { content });
  }

  async updateTaskReflection(id: string, reflection: any): Promise<Task> {
    return apiClient.put<Task>(`/tasks/${id}/reflection`, reflection);
  }
}

export const taskService = new TaskService();
