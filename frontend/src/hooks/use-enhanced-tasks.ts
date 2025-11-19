import { useState, useEffect } from 'react';
import { Task, CreateTaskRequest, UpdateTaskRequest } from '../types';
import { taskService, TaskFilters } from '../services/task-service';
import { useNotification } from '../contexts/NotificationContext';
import { useLoading } from '../contexts/LoadingContext';
import { parseError, getUserFriendlyMessage } from '../utils/error-handling';

// Hook for getting all tasks with filters
export function useTasks(filters?: TaskFilters) {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { showError } = useNotification();

  const fetchTasks = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await taskService.getTasks(filters);
      setTasks(data);
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      setError(message);
      showError(message);
      console.error('Failed to fetch tasks:', errorInfo);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTasks();
  }, [JSON.stringify(filters)]);

  return {
    tasks,
    loading,
    error,
    refetch: fetchTasks,
  };
}

// Hook for getting today's tasks
export function useTodayTasks() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { showError } = useNotification();

  const fetchTodayTasks = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await taskService.getTodayTasks();
      setTasks(data);
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      setError(message);
      showError(message);
      console.error('Failed to fetch today tasks:', errorInfo);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTodayTasks();
  }, []);

  return {
    tasks,
    loading,
    error,
    refetch: fetchTodayTasks,
  };
}

// Hook for getting actionable tasks
export function useActionableTasks() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { showError } = useNotification();

  const fetchActionableTasks = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await taskService.getActionableTasks();
      setTasks(data);
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      setError(message);
      showError(message);
      console.error('Failed to fetch actionable tasks:', errorInfo);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchActionableTasks();
  }, []);

  return {
    tasks,
    loading,
    error,
    refetch: fetchActionableTasks,
  };
}

// Hook for task mutations (create, update, delete)
export function useTaskMutations() {
  const { showSuccess, showError } = useNotification();
  const { showLoading, hideLoading } = useLoading();

  const createTask = async (taskData: CreateTaskRequest): Promise<Task | null> => {
    try {
      showLoading('Creating task...');
      const newTask = await taskService.createTask(taskData);
      showSuccess('Task created successfully');
      return newTask;
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      showError(`Failed to create task: ${message}`);
      console.error('Failed to create task:', errorInfo);
      return null;
    } finally {
      hideLoading();
    }
  };

  const updateTask = async (id: string, taskData: UpdateTaskRequest): Promise<Task | null> => {
    try {
      showLoading('Updating task...');
      const updatedTask = await taskService.updateTask(id, taskData);
      showSuccess('Task updated successfully');
      return updatedTask;
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      showError(`Failed to update task: ${message}`);
      console.error('Failed to update task:', errorInfo);
      return null;
    } finally {
      hideLoading();
    }
  };

  const deleteTask = async (id: string): Promise<boolean> => {
    try {
      showLoading('Deleting task...');
      await taskService.deleteTask(id);
      showSuccess('Task deleted successfully');
      return true;
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      showError(`Failed to delete task: ${message}`);
      console.error('Failed to delete task:', errorInfo);
      return false;
    } finally {
      hideLoading();
    }
  };

  const completeTask = async (id: string): Promise<Task | null> => {
    try {
      showLoading('Completing task...');
      const completedTask = await taskService.completeTask(id);
      showSuccess('Task completed successfully');
      return completedTask;
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      showError(`Failed to complete task: ${message}`);
      console.error('Failed to complete task:', errorInfo);
      return null;
    } finally {
      hideLoading();
    }
  };

  const reorderTask = async (id: string, newOrder: number): Promise<Task | null> => {
    try {
      const reorderedTask = await taskService.reorderTask(id, newOrder);
      showSuccess('Task reordered successfully');
      return reorderedTask;
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      showError(`Failed to reorder task: ${message}`);
      console.error('Failed to reorder task:', errorInfo);
      return null;
    }
  };

  const addTaskNote = async (id: string, content: string): Promise<Task | null> => {
    try {
      showLoading('Adding note...');
      const updatedTask = await taskService.addTaskNote(id, content);
      showSuccess('Note added successfully');
      return updatedTask;
    } catch (err) {
      const errorInfo = parseError(err);
      const message = getUserFriendlyMessage(errorInfo.code);
      showError(`Failed to add note: ${message}`);
      console.error('Failed to add note:', errorInfo);
      return null;
    } finally {
      hideLoading();
    }
  };

  return {
    createTask,
    updateTask,
    deleteTask,
    completeTask,
    reorderTask,
    addTaskNote,
  };
}