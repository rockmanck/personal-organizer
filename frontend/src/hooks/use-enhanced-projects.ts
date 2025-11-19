import { useState, useEffect } from 'react';
import { Project, CreateProjectRequest, UpdateProjectRequest } from '../types';
import { projectService } from '../services/project-service';
import { useNotification } from '../contexts/NotificationContext';
import { useLoading } from '../contexts/LoadingContext';
import { parseError, getUserFriendlyMessage } from '../utils/error-handling';

// Hook for getting all projects
export function useProjects() {
  const [projects, setProjects] = useState<Project[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { showError } = useNotification();

  const fetchProjects = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await projectService.getProjects();
      setProjects(data);
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      setError(message);
      showError(message);
      console.error('Failed to fetch projects:', errorInfo);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProjects();
  }, []);

  return {
    projects,
    loading,
    error,
    refetch: fetchProjects,
  };
}

// Hook for getting a single project
export function useProject(id: string) {
  const [project, setProject] = useState<Project | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { showError } = useNotification();

  const fetchProject = async () => {
    if (!id) return;

    try {
      setLoading(true);
      setError(null);
      const data = await projectService.getProject(id);
      setProject(data);
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      setError(message);
      showError(message);
      console.error('Failed to fetch project:', errorInfo);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProject();
  }, [id]);

  return {
    project,
    loading,
    error,
    refetch: fetchProject,
  };
}

// Hook for getting project statistics
export function useProjectStatistics(id: string) {
  const [statistics, setStatistics] = useState<any>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { showError } = useNotification();

  const fetchStatistics = async () => {
    if (!id) return;

    try {
      setLoading(true);
      setError(null);
      const data = await projectService.getProjectStatistics(id);
      setStatistics(data);
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      setError(message);
      showError(message);
      console.error('Failed to fetch project statistics:', errorInfo);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchStatistics();
  }, [id]);

  return {
    statistics,
    loading,
    error,
    refetch: fetchStatistics,
  };
}

// Hook for project mutations (create, update, delete)
export function useProjectMutations() {
  const { showSuccess, showError } = useNotification();
  const { showLoading, hideLoading } = useLoading();

  const createProject = async (projectData: CreateProjectRequest): Promise<Project | null> => {
    try {
      showLoading('Creating project...');
      const newProject = await projectService.createProject(projectData);
      showSuccess('Project created successfully');
      return newProject;
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      showError(`Failed to create project: ${message}`);
      console.error('Failed to create project:', errorInfo);
      return null;
    } finally {
      hideLoading();
    }
  };

  const updateProject = async (id: string, projectData: UpdateProjectRequest): Promise<Project | null> => {
    try {
      showLoading('Updating project...');
      const updatedProject = await projectService.updateProject(id, projectData);
      showSuccess('Project updated successfully');
      return updatedProject;
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      showError(`Failed to update project: ${message}`);
      console.error('Failed to update project:', errorInfo);
      return null;
    } finally {
      hideLoading();
    }
  };

  const deleteProject = async (id: string): Promise<boolean> => {
    try {
      showLoading('Deleting project...');
      await projectService.deleteProject(id);
      showSuccess('Project deleted successfully');
      return true;
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      showError(`Failed to delete project: ${message}`);
      console.error('Failed to delete project:', errorInfo);
      return false;
    } finally {
      hideLoading();
    }
  };

  const updateProjectPosition = async (id: string, position: { x: number; y: number }): Promise<Project | null> => {
    try {
      const updatedProject = await projectService.updateProjectPosition(id, position);
      showSuccess('Project position updated');
      return updatedProject;
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      showError(`Failed to update project position: ${message}`);
      console.error('Failed to update project position:', errorInfo);
      return null;
    }
  };

  return {
    createProject,
    updateProject,
    deleteProject,
    updateProjectPosition,
  };
}