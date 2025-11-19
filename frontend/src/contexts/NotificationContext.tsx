import { createContext, useContext, useState, useMemo, ReactNode } from 'react';
import { Alert, AlertColor, Snackbar, Slide, SlideProps, IconButton } from '@mui/material';
import { Close as CloseIcon } from '@mui/icons-material';

interface NotificationContextType {
  showSuccess: (message: string) => void;
  showError: (message: string) => void;
  showWarning: (message: string) => void;
  showInfo: (message: string) => void;
  hideNotification: () => void;
}

interface NotificationState {
  open: boolean;
  message: string;
  severity: AlertColor;
}

const NotificationContext = createContext<NotificationContextType | undefined>(undefined);

function SlideTransition(props: Readonly<SlideProps>) {
  return <Slide {...props} direction="up" />;
}

interface NotificationProviderProps {
  readonly children: ReactNode;
}

export function NotificationProvider({ children }: Readonly<NotificationProviderProps>) {
  const [notification, setNotification] = useState<NotificationState>({
    open: false,
    message: '',
    severity: 'info',
  });

  const showNotification = (message: string, severity: AlertColor) => {
    setNotification({
      open: true,
      message,
      severity,
    });
  };

  const hideNotification = () => {
    setNotification(prev => ({ ...prev, open: false }));
  };

  const contextValue: NotificationContextType = useMemo(() => ({
    showSuccess: (message: string) => showNotification(message, 'success'),
    showError: (message: string) => showNotification(message, 'error'),
    showWarning: (message: string) => showNotification(message, 'warning'),
    showInfo: (message: string) => showNotification(message, 'info'),
    hideNotification,
  }), []);

  return (
    <NotificationContext.Provider value={contextValue}>
      {children}
      <Snackbar
        open={notification.open}
        autoHideDuration={6000}
        onClose={hideNotification}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
        TransitionComponent={SlideTransition}
      >
        <Alert
          onClose={hideNotification}
          severity={notification.severity}
          variant="filled"
          sx={{ width: '100%' }}
          action={
            <IconButton
              size="small"
              aria-label="close"
              color="inherit"
              onClick={hideNotification}
            >
              <CloseIcon fontSize="small" />
            </IconButton>
          }
        >
          {notification.message}
        </Alert>
      </Snackbar>
    </NotificationContext.Provider>
  );
}

export function useNotification(): NotificationContextType {
  const context = useContext(NotificationContext);
  if (context === undefined) {
    throw new Error('useNotification must be used within a NotificationProvider');
  }
  return context;
}