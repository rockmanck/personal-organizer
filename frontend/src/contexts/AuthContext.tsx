import React, {createContext, ReactNode, useContext, useEffect, useMemo, useState} from 'react';
import {User} from '@/types';

interface AuthContextType {
  user: User | null;
  loading: boolean;
  login: (token: string) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // Check for existing token in localStorage
    const storedToken = localStorage.getItem('auth_token');
    if (storedToken) {
      setToken(storedToken);
      fetchCurrentUser(storedToken);
    } else {
      setIsLoading(false);
    }
  }, []);

  const fetchCurrentUser = async (authToken: string) => {
    console.log('fetchCurrentUser called with token:', authToken ? 'present' : 'missing');
    try {
      console.log('Making request to /api/v1/auth/me');
      const response = await fetch('/api/v1/auth/me', {
        headers: {
          'Authorization': `Bearer ${authToken}`,
        },
      });

      console.log('Response status:', response.status, response.statusText);
      if (response.ok) {
        const userData = await response.json();
        console.log('User data received:', userData);
        setUser(userData);
      } else {
        console.log('Response not ok, removing token');
        // Token is invalid, remove it
        localStorage.removeItem('auth_token');
        setToken(null);
      }
    } catch (error) {
      console.error('Failed to fetch current user:', error);
      localStorage.removeItem('auth_token');
      setToken(null);
    } finally {
      setIsLoading(false);
    }
  };

  const login = async (newToken: string) => {
    console.log('Login called with token:', newToken ? 'present' : 'missing');
    localStorage.setItem('auth_token', newToken);
    setToken(newToken);
    await fetchCurrentUser(newToken);
  };

  const logout = () => {
    localStorage.removeItem('auth_token');
    setToken(null);
    setUser(null);
  };

  const value: AuthContextType = useMemo(() => ({
    user,
    loading: isLoading,
    login,
    logout,
    isAuthenticated: !!user && !!token,
  }), [user, isLoading, login, logout, token]);

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
