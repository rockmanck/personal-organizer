import { createTheme } from '@mui/material/styles';

// Jedi Organizer custom theme
const theme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#1976d2', // Blue - for primary actions
      light: '#42a5f5',
      dark: '#1565c0',
    },
    secondary: {
      main: '#9c27b0', // Purple - for secondary actions
      light: '#ba68c8',
      dark: '#7b1fa2',
    },
    success: {
      main: '#2e7d32', // Green - for completed tasks
    },
    warning: {
      main: '#ed6c02', // Orange - for pending tasks
    },
    error: {
      main: '#d32f2f', // Red - for urgent tasks
    },
    background: {
      default: '#f5f5f5',
      paper: '#ffffff',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
    h1: {
      fontSize: '2.5rem',
      fontWeight: 500,
    },
    h2: {
      fontSize: '2rem',
      fontWeight: 500,
    },
    h3: {
      fontSize: '1.75rem',
      fontWeight: 500,
    },
    h4: {
      fontSize: '1.5rem',
      fontWeight: 500,
    },
    h5: {
      fontSize: '1.25rem',
      fontWeight: 500,
    },
    h6: {
      fontSize: '1rem',
      fontWeight: 600,
    },
  },
  spacing: 8,
  shape: {
    borderRadius: 8,
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          borderRadius: 8,
        },
      },
    },
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: 12,
          boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        },
      },
    },
    MuiAppBar: {
      styleOverrides: {
        root: {
          boxShadow: '0 1px 3px rgba(0,0,0,0.12)',
        },
      },
    },
  },
});

export default theme;
