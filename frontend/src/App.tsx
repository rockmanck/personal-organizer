import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { Container } from '@mui/material';

import Layout from './components/layout/Layout';
import Dashboard from './components/modes/Dashboard';
import ActMode from './components/modes/act/ActMode';
import PlanMode from './components/modes/plan/PlanMode';
import ReflectMode from './components/modes/reflect/ReflectMode';
import NotFound from './components/common/NotFound';

/**
 * Main App component implementing the three-mode interface
 * (Plan/Act/Reflect) according to Jedi Techniques methodology.
 */
function App() {
  return (
    <Layout>
      <Container maxWidth="xl" sx={{ py: 2 }}>
        <Routes>
          {/* Dashboard - Mode selection */}
          <Route path="/" element={<Dashboard />} />
          
          {/* ACT Mode - Task execution */}
          <Route path="/act" element={<ActMode />} />
          
          {/* PLAN Mode - Project management */}
          <Route path="/plan" element={<PlanMode />} />
          
          {/* REFLECT Mode - Analytics and reflection */}
          <Route path="/reflect" element={<ReflectMode />} />
          
          {/* 404 Not Found */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </Container>
    </Layout>
  );
}

export default App;
