import React from 'react';
import { Typography, Box, Paper } from '@mui/material';

/**
 * ACT Mode component for task execution.
 * Provides a minimalistic interface for completing tasks efficiently.
 */
const ActMode: React.FC = () => {
  return (
    <Box sx={{ p: 3 }}>
      <Paper sx={{ p: 4, textAlign: 'center' }}>
        <Typography variant="h4" component="h1" gutterBottom>
          ACT Mode
        </Typography>
        <Typography variant="body1" color="text.secondary">
          Execute tasks with focus and clarity.
        </Typography>
        <Typography variant="body2" sx={{ mt: 2 }}>
          Task execution interface will be implemented here.
        </Typography>
      </Paper>
    </Box>
  );
};

export default ActMode;
