import {apiClient} from './api-client';
import {CreateProjectRequest, Project, UpdateProjectRequest} from '../types';

export class ProjectService {
  async getProjects(): Promise<Project[]> {
    return apiClient.get<Project[]>('/projects');
  }

  async getProject(id: string): Promise<Project> {
    return apiClient.get<Project>(`/projects/${id}`);
  }

  async createProject(projectData: CreateProjectRequest): Promise<Project> {
    return apiClient.post<Project>('/projects', projectData);
  }

  async updateProject(id: string, projectData: UpdateProjectRequest): Promise<Project> {
    return apiClient.put<Project>(`/projects/${id}`, projectData);
  }

  async deleteProject(id: string): Promise<void> {
    return apiClient.delete<void>(`/projects/${id}`);
  }

  async updateProjectPosition(id: string, position: { x: number; y: number }): Promise<Project> {
    return apiClient.patch<Project>(`/projects/${id}/position`, { position });
  }

  async getProjectStatistics(id: string): Promise<any> {
    return apiClient.get<any>(`/projects/${id}/statistics`);
  }
}

export const projectService = new ProjectService();
