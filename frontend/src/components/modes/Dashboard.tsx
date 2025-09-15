import React from 'react';
import {
  Avatar,
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Chip,
  CircularProgress,
  Grid,
  Paper,
  Typography
} from '@mui/material';
import {
  Analytics as ReflectIcon,
  Folder as ProjectIcon,
  PlayArrow as ActIcon,
  Task as TaskIcon,
  Timeline as PlanIcon
} from '@mui/icons-material';
import {useNavigate} from 'react-router-dom';
import {useProjects, useTodayTasks, useUser} from '../../hooks';

const Dashboard: React.FC = () => {
  const navigate = useNavigate();
  const { user, loading: userLoading } = useUser();
  const { tasks: todayTasks, loading: tasksLoading } = useTodayTasks();
  const { projects, loading: projectsLoading } = useProjects();

  const modes = [
    {
      id: 'act',
      title: 'ACT',
      description: 'Execute tasks with focus and clarity. Complete your current work efficiently.',
      icon: <ActIcon sx={{ fontSize: 48 }} />,
      color: '#2e7d32',
      path: '/act',
      stats: tasksLoading ? '...' : `${todayTasks.filter(t => t.status === 'TODO').length} tasks today`
    },
    {
      id: 'plan',
      title: 'PLAN',
      description: 'Organize projects and set priorities. Plan your work strategically.',
      icon: <PlanIcon sx={{ fontSize: 48 }} />,
      color: '#1976d2',
      path: '/plan',
      stats: projectsLoading ? '...' : `${projects.filter(p => p.status === 'ACTIVE').length} active projects`
    },
    {
      id: 'reflect',
      title: 'REFLECT',
      description: 'Review progress and gain insights. Reflect on your productivity patterns.',
      icon: <ReflectIcon sx={{ fontSize: 48 }} />,
      color: '#9c27b0',
      path: '/reflect',
      stats: 'Analytics available'
    }
  ];

  const handleModeSelect = (path: string) => {
    navigate(path);
  };

  const isLoading = userLoading || tasksLoading || projectsLoading;

  return (
    <Box sx={{ p: 3 }}>
      {/* Welcome section */}
      <Paper sx={{ p: 4, mb: 4 }}>
        <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', mb: 3 }}>
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
            {userLoading ? (
              <CircularProgress size={40} />
            ) : (
              <Avatar
                src={user?.profileImageUrl}
                alt={user?.displayName}
                sx={{ width: 48, height: 48 }}
              >
                {user?.firstName?.charAt(0) || 'U'}
              </Avatar>
            )}
            <Box>
              <Typography variant="h4" component="h1">
                {userLoading ? 'Loading...' : `Welcome back, ${user?.firstName || 'User'}`}
              </Typography>
              <Typography variant="body1" color="text.secondary">
                Choose your mode to continue your productivity journey
              </Typography>
            </Box>
          </Box>

          {!isLoading && (
            <Box sx={{ display: 'flex', gap: 2 }}>
              <Chip
                icon={<TaskIcon />}
                label={`${todayTasks.length} tasks today`}
                color="primary"
                variant="outlined"
              />
              <Chip
                icon={<ProjectIcon />}
                label={`${projects.length} projects`}
                color="secondary"
                variant="outlined"
              />
            </Box>
          )}
        </Box>
      </Paper>

      {/* Mode selection cards */}
      <Grid container spacing={4}>
        {modes.map((mode) => (
          <Grid item xs={12} md={4} key={mode.id}>
            <Card
              sx={{
                height: '100%',
                display: 'flex',
                flexDirection: 'column',
                transition: 'all 0.2s ease-in-out',
                '&:hover': {
                  transform: 'translateY(-4px)',
                  boxShadow: 4
                }
              }}
            >
              <CardContent sx={{ flexGrow: 1, textAlign: 'center', p: 3 }}>
                <Box sx={{ color: mode.color, mb: 2 }}>
                  {mode.icon}
                </Box>

                <Typography variant="h4" component="h2" gutterBottom>
                  {mode.title}
                </Typography>

                <Typography variant="body1" color="text.secondary" paragraph>
                  {mode.description}
                </Typography>

                <Chip
                  label={mode.stats}
                  size="small"
                  variant="outlined"
                  sx={{ bgcolor: 'background.default' }}
                />
              </CardContent>

              <CardActions sx={{ p: 3, pt: 0 }}>
                <Button
                  variant="contained"
                  fullWidth
                  size="large"
                  sx={{
                    bgcolor: mode.color,
                    '&:hover': {
                      bgcolor: mode.color,
                      filter: 'brightness(0.9)'
                    }
                  }}
                  onClick={() => handleModeSelect(mode.path)}
                >
                  Enter {mode.title} Mode
                </Button>
              </CardActions>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
};

export default Dashboard;
