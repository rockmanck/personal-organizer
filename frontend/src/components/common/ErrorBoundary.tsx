import { Component, ErrorInfo, ReactNode } from 'react';
import { Alert, Box, Button, Typography } from '@mui/material';
import { Refresh as RefreshIcon } from '@mui/icons-material';

interface Props {
  children: ReactNode;
  fallback?: ReactNode;
}

interface State {
  hasError: boolean;
  error?: Error;
  errorInfo?: ErrorInfo;
}

/**
 * Error Boundary component to catch and handle React errors gracefully.
 * Provides user-friendly error messages and recovery options.
 */
class ErrorBoundary extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError(error: Error): State {
    // Update state so the next render will show the fallback UI
    return { hasError: true, error };
  }

  componentDidCatch(error: Error, errorInfo: ErrorInfo) {
    // Log the error for debugging
    console.error('ErrorBoundary caught an error:', error, errorInfo);
    
    this.setState({
      error,
      errorInfo
    });
  }

  handleRetry = () => {
    this.setState({ hasError: false, error: undefined, errorInfo: undefined });
  };

  handleReload = () => {
    window.location.reload();
  };

  render() {
    if (this.state.hasError) {
      // Custom fallback UI provided by parent
      if (this.props.fallback) {
        return this.props.fallback;
      }

      // Default error UI
      return (
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            minHeight: '50vh',
            textAlign: 'center',
            p: 3,
          }}
        >
          <Alert 
            severity="error" 
            sx={{ mb: 3, maxWidth: 600 }}
            action={
              <Box sx={{ display: 'flex', gap: 1 }}>
                <Button 
                  color="inherit" 
                  size="small" 
                  onClick={this.handleRetry}
                >
                  Try Again
                </Button>
                <Button 
                  color="inherit" 
                  size="small" 
                  onClick={this.handleReload}
                  startIcon={<RefreshIcon />}
                >
                  Reload Page
                </Button>
              </Box>
            }
          >
            <Typography variant="h6" component="div" gutterBottom>
              Something went wrong
            </Typography>
            <Typography variant="body2">
              An unexpected error occurred. Please try again or reload the page.
            </Typography>
          </Alert>

          {/* Show error details in development */}
          {import.meta.env.MODE === 'development' && this.state.error && (
            <Box sx={{ mt: 2, textAlign: 'left', maxWidth: 800 }}>
              <Typography variant="subtitle2" color="error" gutterBottom>
                Error Details (Development Mode):
              </Typography>
              <Typography 
                variant="body2" 
                component="pre" 
                sx={{ 
                  backgroundColor: '#f5f5f5',
                  p: 2,
                  borderRadius: 1,
                  overflow: 'auto',
                  fontSize: '0.8rem'
                }}
              >
                {this.state.error.toString()}
                {this.state.errorInfo && this.state.errorInfo.componentStack}
              </Typography>
            </Box>
          )}
        </Box>
      );
    }

    return this.props.children;
  }
}

export default ErrorBoundary;