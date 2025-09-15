import React from 'react';
import {
  Alert,
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Chip,
  CircularProgress,
  Fab,
  Grid,
  IconButton,
  Paper,
  Tooltip,
  Typography
} from '@mui/material';
import {Add as AddIcon, Delete as DeleteIcon, Edit as EditIcon, Refresh as RefreshIcon} from '@mui/icons-material';
import {useProjects} from '../../../hooks';
import {Project, ProjectPriority, ProjectStatus} from '../../../types';

const PlanMode: React.FC = () => {
  const { projects, loading, error, refetch } = useProjects();

  const getStatusColor = (status: ProjectStatus) => {
    switch (status) {
      case ProjectStatus.ACTIVE: return 'success';
      case ProjectStatus.ON_HOLD: return 'warning';
      case ProjectStatus.COMPLETED: return 'info';
      case ProjectStatus.ARCHIVED: return 'default';
      default: return 'default';
    }
  };

  const getPriorityColor = (priority: ProjectPriority) => {
    switch (priority) {
      case ProjectPriority.CRITICAL: return 'error';
      case ProjectPriority.HIGH: return 'warning';
      case ProjectPriority.MEDIUM: return 'info';
      case ProjectPriority.LOW: return 'default';
      default: return 'default';
    }
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString();
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

  const activeProjects = projects.filter(p => p.status !== ProjectStatus.ARCHIVED);

  return (
    <Box sx={{ p: 3, position: 'relative', minHeight: '80vh' }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" component="h1">
          PLAN Mode
        </Typography>
        <Tooltip title="Refresh projects">
          <IconButton onClick={refetch}>
            <RefreshIcon />
          </IconButton>
        </Tooltip>
      </Box>

      <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
        Organize projects and set priorities.
      </Typography>

      {activeProjects.length === 0 ? (
        <Paper sx={{ p: 4, textAlign: 'center' }}>
          <Typography variant="h6" color="text.secondary">
            No active projects
          </Typography>
          <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
            Create your first project to start planning.
          </Typography>
        </Paper>
      ) : (
        <Grid container spacing={3}>
          {activeProjects.map((project: Project) => (
            <Grid item xs={12} sm={6} md={4} key={project.id}>
              <Card
                sx={{
                  height: '100%',
                  display: 'flex',
                  flexDirection: 'column',
                  transition: 'transform 0.2s',
                  '&:hover': {
                    transform: 'translateY(-4px)',
                    boxShadow: 4
                  }
                }}
              >
                <CardContent sx={{ flexGrow: 1 }}>
                  <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', mb: 2 }}>
                    <Typography variant="h6" component="h2" noWrap>
                      {project.title}
                    </Typography>
                    <Box sx={{ display: 'flex', gap: 1 }}>
                      <Chip
                        label={project.status}
                        size="small"
                        color={getStatusColor(project.status)}
                      />
                      <Chip
                        label={project.priority}
                        size="small"
                        color={getPriorityColor(project.priority)}
                      />
                    </Box>
                  </Box>

                  {project.description && (
                    <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                      {project.description}
                    </Typography>
                  )}

                  <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <Typography variant="caption" color="text.secondary">
                      {project.taskIds.length} task{project.taskIds.length !== 1 ? 's' : ''}
                    </Typography>
                    {project.deadline && (
                      <Typography variant="caption" color="text.secondary">
                        Due: {formatDate(project.deadline)}
                      </Typography>
                    )}
                  </Box>

                  {project.tags.length > 0 && (
                    <Box sx={{ mt: 2, display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                      {project.tags.map((tag) => (
                        <Chip key={`${project.id}-${tag}`} label={tag} size="small" variant="outlined" />
                      ))}
                    </Box>
                  )}
                </CardContent>

                <CardActions sx={{ justifyContent: 'space-between' }}>
                  <Button size="small" startIcon={<EditIcon />}>
                    Edit
                  </Button>
                  <IconButton size="small" color="error">
                    <DeleteIcon />
                  </IconButton>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>
      )}

      <Fab
        color="primary"
        aria-label="add project"
        sx={{
          position: 'fixed',
          bottom: 24,
          right: 24,
        }}
      >
        <AddIcon />
      </Fab>

      <Box sx={{ mt: 3, textAlign: 'center' }}>
        <Typography variant="body2" color="text.secondary">
          {projects.length} total projects • {activeProjects.length} active
        </Typography>
      </Box>
    </Box>
  );
};

export default PlanMode;
