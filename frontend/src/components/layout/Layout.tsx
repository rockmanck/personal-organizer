import React, { ReactNode } from 'react';
import { AppBar, Toolbar, Typography, IconButton, Box } from '@mui/material';
import { Menu as MenuIcon } from '@mui/icons-material';

interface LayoutProps {
  children: ReactNode;
}

/**
 * Main layout component providing the application structure
 * with header, navigation, and content area.
 */
const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      {/* Header */}
      <AppBar position="static" color="primary">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Jedi Organizer
          </Typography>
          
          <Typography variant="body2" color="inherit" sx={{ ml: 2 }}>
            v1.0.0
          </Typography>
        </Toolbar>
      </AppBar>

      {/* Main content area */}
      <Box component="main" sx={{ flexGrow: 1, bgcolor: 'background.default' }}>
        {children}
      </Box>
    </Box>
  );
};

export default Layout;
