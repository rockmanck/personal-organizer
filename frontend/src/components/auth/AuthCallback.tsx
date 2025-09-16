import React, {useEffect, useState} from 'react';
import {useNavigate, useSearchParams} from 'react-router-dom';
import {Alert, Box, CircularProgress, Typography} from '@mui/material';
import {useAuth} from '../../contexts/AuthContext';

const AuthCallback: React.FC = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { login } = useAuth();
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const handleCallback = async () => {
      const token = searchParams.get('token');
      const errorParam = searchParams.get('error');

      console.log('AuthCallback: token from URL:', token ? 'present' : 'missing');
      console.log('AuthCallback: error from URL:', errorParam);

      if (errorParam) {
        setError('Authentication failed. Please try again.');
        setTimeout(() => navigate('/login'), 3000);
        return;
      }

      if (token) {
        try {
          console.log('AuthCallback: calling login with token');
          // Wait for login to complete before navigating
          await login(token);
          console.log('AuthCallback: login completed, navigating to dashboard');
          navigate('/', { replace: true });
        } catch (error) {
          console.error('Login failed:', error);
          setError('Login failed. Please try again.');
          setTimeout(() => navigate('/login'), 3000);
        }
      } else {
        setError('No authentication token received.');
        setTimeout(() => navigate('/login'), 3000);
      }
    };

    handleCallback();
  }, [searchParams, login, navigate]);

  if (error) {
    return (
      <Box
        sx={{
          minHeight: '100vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          flexDirection: 'column',
          gap: 2,
        }}
      >
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
        <Typography variant="body2" color="text.secondary">
          Redirecting to login page...
        </Typography>
      </Box>
    );
  }

  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        flexDirection: 'column',
        gap: 2,
      }}
    >
      <CircularProgress size={60} />
      <Typography variant="h6" color="text.secondary">
        Completing sign in...
      </Typography>
    </Box>
  );
};

export default AuthCallback;
