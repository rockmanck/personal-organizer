import React from 'react';
import {Route, Routes} from 'react-router-dom';
import {Container} from '@mui/material';

import Layout from './components/layout/Layout';
import Dashboard from './components/modes/Dashboard';
import ActMode from './components/modes/act/ActMode';
import PlanMode from './components/modes/plan/PlanMode';
import ReflectMode from './components/modes/reflect/ReflectMode';
import NotFound from './components/common/NotFound';
import Login from './components/auth/Login';
import AuthCallback from './components/auth/AuthCallback';
import PrivateRoute from './components/auth/PrivateRoute';
import {AuthProvider} from './contexts/AuthContext';

/**
 * Main App component implementing the three-mode interface
 * (Plan/Act/Reflect) according to Jedi Techniques methodology.
 * Includes authentication flow with Google OAuth2.
 */
function App() {
  return (
    <AuthProvider>
      <Routes>
        {/* Public routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/auth/callback" element={<AuthCallback />} />

        {/* Protected routes */}
        <Route path="/" element={
          <PrivateRoute>
            <Layout>
              <Container maxWidth="xl" sx={{ py: 2 }}>
                <Dashboard />
              </Container>
            </Layout>
          </PrivateRoute>
        } />

        <Route path="/act" element={
          <PrivateRoute>
            <Layout>
              <Container maxWidth="xl" sx={{ py: 2 }}>
                <ActMode />
              </Container>
            </Layout>
          </PrivateRoute>
        } />

        <Route path="/plan" element={
          <PrivateRoute>
            <Layout>
              <Container maxWidth="xl" sx={{ py: 2 }}>
                <PlanMode />
              </Container>
            </Layout>
          </PrivateRoute>
        } />

        <Route path="/reflect" element={
          <PrivateRoute>
            <Layout>
              <Container maxWidth="xl" sx={{ py: 2 }}>
                <ReflectMode />
              </Container>
            </Layout>
          </PrivateRoute>
        } />

        {/* 404 Not Found */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </AuthProvider>
  );
}

export default App;
