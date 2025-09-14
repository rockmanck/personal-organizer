import React from 'react';
import { Typography, Box, Paper, Button } from '@mui/material';
import { Home as HomeIcon } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';

/**
 * NotFound component for 404 errors.
 */
const NotFound: React.FC = () => {
  const navigate = useNavigate();

  return (
    <Box sx={{ p: 3, textAlign: 'center' }}>
      <Paper sx={{ p: 6 }}>
        <Typography variant="h2" component="h1" gutterBottom>
          404
        </Typography>
        <Typography variant="h5" color="text.secondary" gutterBottom>
          Page Not Found
        </Typography>
        <Typography variant="body1" sx={{ mb: 4 }}>
          The page you're looking for doesn't exist.
        </Typography>
        <Button
          variant="contained"
          startIcon={<HomeIcon />}
          onClick={() => navigate('/')}
        >
          Go Home
        </Button>
      </Paper>
    </Box>
  );
};

export default NotFound;
