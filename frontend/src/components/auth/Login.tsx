import React from 'react';
import {Box, Button, Container, Paper, Typography,} from '@mui/material';
import {Google as GoogleIcon} from '@mui/icons-material';

const Login: React.FC = () => {
  const handleGoogleLogin = () => {
    window.location.href = import.meta.env.VITE_GOOGLE_OAUTH_URL || 'http://localhost:8080/oauth2/authorization/google';
  };

  return (
    <Container maxWidth="sm">
      <Box
        sx={{
          minHeight: '100vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
        }}
      >
        <Paper
          elevation={3}
          sx={{
            p: 4,
            width: '100%',
            textAlign: 'center',
          }}
        >
          <Typography variant="h3" component="h1" gutterBottom>
            Jedi Organizer
          </Typography>

          <Typography variant="h6" color="text.secondary" sx={{ mb: 4 }}>
            Master your productivity with Jedi Techniques
          </Typography>

          <Typography variant="body1" color="text.secondary" sx={{ mb: 4 }}>
            Sign in to access your personalized productivity dashboard with
            Plan, Act, and Reflect modes.
          </Typography>

          <Button
            variant="contained"
            size="large"
            startIcon={<GoogleIcon />}
            onClick={handleGoogleLogin}
            sx={{
              py: 1.5,
              px: 4,
              fontSize: '1.1rem',
              backgroundColor: '#4285f4',
              '&:hover': {
                backgroundColor: '#357ae8',
              },
            }}
          >
            Sign in with Google
          </Button>

          <Typography variant="caption" display="block" sx={{ mt: 3, color: 'text.secondary' }}>
            We use Google OAuth for secure authentication.
            <br />
            Your data stays private and secure.
          </Typography>
        </Paper>
      </Box>
    </Container>
  );
};

export default Login;
