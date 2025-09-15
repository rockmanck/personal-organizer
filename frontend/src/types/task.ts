export enum TaskStatus {
  TODO = 'TODO',
  IN_PROGRESS = 'IN_PROGRESS',
  DONE = 'DONE',
  CANCELLED = 'CANCELLED'
}

export enum TaskType {
  ACTION = 'ACTION',
  WAITING_FOR = 'WAITING_FOR',
  REFERENCE = 'REFERENCE',
  SOMEDAY_MAYBE = 'SOMEDAY_MAYBE'
}

export interface TaskNote {
  content: string;
  createdAt: string;
}

export interface TaskReflection {
  difficulty: number;
  satisfaction: number;
  notes?: string;
  lessonsLearned?: string;
}

export interface Task {
  id: string;
  userId: string;
  projectId?: string;
  title: string;
  description?: string;
  status: TaskStatus;
  type: TaskType;
  createdAt: string;
  updatedAt: string;
  completedAt?: string;
  scheduledFor?: string;
  estimatedPomodoros?: number;
  actualPomodoros?: number;
  userDefinedOrder: number;
  context?: string;
  energyLevel?: number;
  notes: TaskNote[];
  reflection?: TaskReflection;
  tags: string[];
}

export interface CreateTaskRequest {
  title: string;
  description?: string;
  projectId?: string;
  type?: TaskType;
  scheduledFor?: string;
  estimatedPomodoros?: number;
  context?: string;
  energyLevel?: number;
  tags?: string[];
}

export interface UpdateTaskRequest {
  title?: string;
  description?: string;
  projectId?: string;
  status?: TaskStatus;
  type?: TaskType;
  scheduledFor?: string;
  estimatedPomodoros?: number;
  actualPomodoros?: number;
  userDefinedOrder?: number;
  context?: string;
  energyLevel?: number;
  tags?: string[];
}
