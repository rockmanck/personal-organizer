import React, {useState} from 'react';
import {
  Alert,
  Box,
  Checkbox,
  Chip,
  CircularProgress,
  IconButton,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  Paper,
  Tooltip,
  Typography
} from '@mui/material';
import {Add as AddIcon, Refresh as RefreshIcon} from '@mui/icons-material';
import {useTodayTasks} from '../../../hooks';
import {CreateTaskRequest, Task, TaskStatus} from '../../../types';
import {taskService} from '../../../services';
import TaskForm from '../../common/TaskForm';

const ActMode: React.FC = () => {
  const { tasks, loading, error, refetch } = useTodayTasks();
  const [completingTasks, setCompletingTasks] = useState<Set<string>>(new Set());
  const [isTaskFormOpen, setIsTaskFormOpen] = useState(false);
  const [isCreatingTask, setIsCreatingTask] = useState(false);

  const handleTaskComplete = async (taskId: string) => {
    setCompletingTasks(prev => new Set([...prev, taskId]));
    try {
      await taskService.completeTask(taskId);
      refetch();
    } catch (err) {
      console.error('Failed to complete task:', err);
    } finally {
      setCompletingTasks(prev => {
        const next = new Set(prev);
        next.delete(taskId);
        return next;
      });
    }
  };

  const handleCreateTask = async (taskData: CreateTaskRequest) => {
    setIsCreatingTask(true);
    try {
      await taskService.createTask(taskData);
      refetch();
    } finally {
      setIsCreatingTask(false);
    }
  };

  if (loading) {
    return (
      <Box sx={{ p: 3, display: 'flex', justifyContent: 'center' }}>
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Box sx={{ p: 3 }}>
        <Alert severity="error" action={
          <IconButton onClick={refetch}>
            <RefreshIcon />
          </IconButton>
        }>
          {error}
        </Alert>
      </Box>
    );
  }

  const todoTasks = tasks.filter(task => task.status === TaskStatus.TODO);

  return (
    <Box sx={{ p: 3 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" component="h1">
          ACT Mode
        </Typography>
        <Box>
          <Tooltip title="Refresh tasks">
            <IconButton onClick={refetch}>
              <RefreshIcon />
            </IconButton>
          </Tooltip>
          <Tooltip title="Add new task">
            <IconButton onClick={() => setIsTaskFormOpen(true)}>
              <AddIcon />
            </IconButton>
          </Tooltip>
        </Box>
      </Box>

      <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
        Focus on executing today's tasks efficiently.
      </Typography>

      {todoTasks.length === 0 ? (
        <Paper sx={{ p: 4, textAlign: 'center' }}>
          <Typography variant="h6" color="text.secondary">
            No tasks for today
          </Typography>
          <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
            All caught up! Add new tasks or plan for tomorrow.
          </Typography>
        </Paper>
      ) : (
        <Paper>
          <List>
            {todoTasks.map((task: Task, index: number) => (
              <ListItem
                key={task.id}
                divider={index < todoTasks.length - 1}
                secondaryAction={
                  <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                    {task.estimatedPomodoros && (
                      <Chip
                        label={`${task.estimatedPomodoros}🍅`}
                        size="small"
                        variant="outlined"
                      />
                    )}
                  </Box>
                }
              >
                <ListItemButton onClick={() => handleTaskComplete(task.id)}>
                  <Checkbox
                    checked={false}
                    disabled={completingTasks.has(task.id)}
                    sx={{ mr: 2 }}
                  />
                  <ListItemText
                    primary={task.title}
                    secondary={task.description}
                    primaryTypographyProps={{
                      variant: 'h6',
                      sx: { fontWeight: 'normal' }
                    }}
                  />
                </ListItemButton>
              </ListItem>
            ))}
          </List>
        </Paper>
      )}

      <Box sx={{ mt: 3, textAlign: 'center' }}>
        <Typography variant="body2" color="text.secondary">
          {tasks.length} total tasks • {todoTasks.length} remaining
        </Typography>
      </Box>

      <TaskForm
        open={isTaskFormOpen}
        onClose={() => setIsTaskFormOpen(false)}
        onSubmit={handleCreateTask}
        loading={isCreatingTask}
      />
    </Box>
  );
};

export default ActMode;
