export enum ProjectStatus {
  ACTIVE = 'ACTIVE',
  ON_HOLD = 'ON_HOLD',
  COMPLETED = 'COMPLETED',
  ARCHIVED = 'ARCHIVED'
}

export enum ProjectPriority {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH',
  CRITICAL = 'CRITICAL'
}

export interface ProjectSettings {
  allowTaskReordering: boolean;
  autoCompleteProject: boolean;
  notifyOnDeadline: boolean;
}

export interface Project {
  id: string;
  userId: string;
  title: string;
  description?: string;
  status: ProjectStatus;
  priority: ProjectPriority;
  deadline?: string;
  createdAt: string;
  updatedAt: string;
  completedAt?: string;
  canvasPosition: {
    x: number;
    y: number;
  };
  taskIds: string[];
  settings: ProjectSettings;
  tags: string[];
}

export interface CreateProjectRequest {
  title: string;
  description?: string;
  priority?: ProjectPriority;
  deadline?: string;
  canvasPosition?: {
    x: number;
    y: number;
  };
  tags?: string[];
}

export interface UpdateProjectRequest {
  title?: string;
  description?: string;
  status?: ProjectStatus;
  priority?: ProjectPriority;
  deadline?: string;
  canvasPosition?: {
    x: number;
    y: number;
  };
  settings?: ProjectSettings;
  tags?: string[];
}
