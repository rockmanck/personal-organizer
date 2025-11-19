import { createContext, useContext, useState, useMemo, ReactNode } from 'react';
import { Backdrop, CircularProgress, Typography, Box } from '@mui/material';

interface LoadingContextType {
  isLoading: boolean;
  setLoading: (loading: boolean, message?: string) => void;
  showLoading: (message?: string) => void;
  hideLoading: () => void;
}

interface LoadingState {
  isLoading: boolean;
  message?: string;
}

const LoadingContext = createContext<LoadingContextType | undefined>(undefined);

interface LoadingProviderProps {
  readonly children: ReactNode;
}

export function LoadingProvider({ children }: Readonly<LoadingProviderProps>) {
  const [loadingState, setLoadingState] = useState<LoadingState>({
    isLoading: false,
    message: undefined,
  });

  const setLoading = (loading: boolean, message?: string) => {
    setLoadingState({
      isLoading: loading,
      message: loading ? message : undefined,
    });
  };

  const showLoading = (message?: string) => {
    setLoading(true, message);
  };

  const hideLoading = () => {
    setLoading(false);
  };

  const contextValue: LoadingContextType = useMemo(() => ({
    isLoading: loadingState.isLoading,
    setLoading,
    showLoading,
    hideLoading,
  }), [loadingState.isLoading]);

  return (
    <LoadingContext.Provider value={contextValue}>
      {children}
      <Backdrop
        sx={{
          color: '#fff',
          zIndex: (theme) => theme.zIndex.drawer + 1,
          display: 'flex',
          flexDirection: 'column',
          gap: 2,
        }}
        open={loadingState.isLoading}
      >
        <CircularProgress color="inherit" size={60} />
        {loadingState.message && (
          <Box textAlign="center">
            <Typography variant="h6" component="div">
              {loadingState.message}
            </Typography>
            <Typography variant="body2" sx={{ mt: 1, opacity: 0.8 }}>
              Please wait...
            </Typography>
          </Box>
        )}
      </Backdrop>
    </LoadingContext.Provider>
  );
}

export function useLoading(): LoadingContextType {
  const context = useContext(LoadingContext);
  if (context === undefined) {
    throw new Error('useLoading must be used within a LoadingProvider');
  }
  return context;
}