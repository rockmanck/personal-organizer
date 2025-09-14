import React from 'react';
import { 
  Typography, 
  Grid, 
  Card, 
  CardContent, 
  CardActions, 
  Button, 
  Box,
  Paper
} from '@mui/material';
import { 
  PlayArrow as ActIcon,
  Timeline as PlanIcon,
  Analytics as ReflectIcon
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';

/**
 * Dashboard component for mode selection.
 * Implements the three-mode interface according to Jedi Techniques methodology.
 */
const Dashboard: React.FC = () => {
  const navigate = useNavigate();

  const modes = [
    {
      id: 'act',
      title: 'ACT',
      description: 'Execute tasks with focus and clarity. Complete your current work efficiently.',
      icon: <ActIcon sx={{ fontSize: 48 }} />,
      color: '#2e7d32', // Green
      path: '/act'
    },
    {
      id: 'plan',
      title: 'PLAN',
      description: 'Organize projects and set priorities. Plan your work strategically.',
      icon: <PlanIcon sx={{ fontSize: 48 }} />,
      color: '#1976d2', // Blue
      path: '/plan'
    },
    {
      id: 'reflect',
      title: 'REFLECT',
      description: 'Review progress and gain insights. Reflect on your productivity patterns.',
      icon: <ReflectIcon sx={{ fontSize: 48 }} />,
      color: '#9c27b0', // Purple
      path: '/reflect'
    }
  ];

  const handleModeSelect = (path: string) => {
    navigate(path);
  };

  return (
    <Box sx={{ p: 3 }}>
      {/* Welcome section */}
      <Paper sx={{ p: 4, mb: 4, textAlign: 'center' }}>
        <Typography variant="h3" component="h1" gutterBottom>
          Welcome to Jedi Organizer
        </Typography>
        <Typography variant="h6" color="text.secondary" paragraph>
          Choose your mode to begin your productivity journey
        </Typography>
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
                
                <Typography variant="body1" color="text.secondary">
                  {mode.description}
                </Typography>
              </CardContent>
              
              <CardActions sx={{ p: 3, pt: 0 }}>
                <Button
                  variant="contained"
                  fullWidth
                  size="large"
                  sx={{ bgcolor: mode.color }}
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
