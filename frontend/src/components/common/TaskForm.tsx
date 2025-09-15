import React, {useState} from 'react';
import {
  Alert,
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField
} from '@mui/material';
import {CreateTaskRequest, TaskType} from '../../types';

interface TaskFormProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (taskData: CreateTaskRequest) => Promise<void>;
  loading?: boolean;
}

const TaskForm: React.FC<TaskFormProps> = ({ open, onClose, onSubmit, loading = false }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [type, setType] = useState<TaskType>(TaskType.ACTION);
  const [estimatedPomodoros, setEstimatedPomodoros] = useState<number | undefined>();
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!title.trim()) {
      setError('Title is required');
      return;
    }

    try {
      setError(null);
      const taskData: CreateTaskRequest = {
        title: title.trim(),
        description: description.trim() || undefined,
        type,
        estimatedPomodoros: estimatedPomodoros || undefined,
      };

      await onSubmit(taskData);
      handleClose();
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to create task');
    }
  };

  const handleClose = () => {
    setTitle('');
    setDescription('');
    setType(TaskType.ACTION);
    setEstimatedPomodoros(undefined);
    setError(null);
    onClose();
  };

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
      <form onSubmit={handleSubmit}>
        <DialogTitle>Create New Task</DialogTitle>

        <DialogContent>
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, pt: 1 }}>
            {error && (
              <Alert severity="error">{error}</Alert>
            )}

            <TextField
              label="Title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
              fullWidth
              autoFocus
            />

            <TextField
              label="Description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              multiline
              rows={3}
              fullWidth
            />

            <FormControl fullWidth>
              <InputLabel>Type</InputLabel>
              <Select
                value={type}
                onChange={(e) => setType(e.target.value as TaskType)}
                label="Type"
              >
                <MenuItem value={TaskType.ACTION}>Action</MenuItem>
                <MenuItem value={TaskType.WAITING_FOR}>Waiting For</MenuItem>
                <MenuItem value={TaskType.REFERENCE}>Reference</MenuItem>
                <MenuItem value={TaskType.SOMEDAY_MAYBE}>Someday/Maybe</MenuItem>
              </Select>
            </FormControl>

            <TextField
              label="Estimated Pomodoros"
              type="number"
              value={estimatedPomodoros || ''}
              onChange={(e) => setEstimatedPomodoros(e.target.value ? parseInt(e.target.value) : undefined)}
              inputProps={{ min: 1, max: 20 }}
              fullWidth
            />
          </Box>
        </DialogContent>

        <DialogActions>
          <Button onClick={handleClose} disabled={loading}>
            Cancel
          </Button>
          <Button type="submit" variant="contained" disabled={loading}>
            {loading ? 'Creating...' : 'Create Task'}
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
};

export default TaskForm;
