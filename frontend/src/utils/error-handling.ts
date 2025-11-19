import { ApiError } from '../types';

/**
 * Centralized error handling utilities for the frontend application.
 * Provides consistent error parsing, logging, and user-friendly message formatting.
 */

export interface ParsedError {
  code: string;
  message: string;
  details?: any;
  originalError?: any;
}

/**
 * Parse different types of errors into a standardized format
 */
export function parseError(error: unknown): ParsedError {
  // API Error from backend
  if (isApiError(error)) {
    return {
      code: error.error.code || 'API_ERROR',
      message: error.error.message || 'An API error occurred',
      details: error.error.details,
      originalError: error
    };
  }

  // Network/Fetch errors
  if (error instanceof TypeError && error.message.includes('fetch')) {
    return {
      code: 'NETWORK_ERROR',
      message: 'Unable to connect to the server. Please check your internet connection.',
      originalError: error
    };
  }

  // Standard JavaScript errors
  if (error instanceof Error) {
    return {
      code: 'JAVASCRIPT_ERROR',
      message: error.message || 'An unexpected error occurred',
      originalError: error
    };
  }

  // String errors
  if (typeof error === 'string') {
    return {
      code: 'STRING_ERROR',
      message: error,
      originalError: error
    };
  }

  // Unknown error type
  return {
    code: 'UNKNOWN_ERROR',
    message: 'An unexpected error occurred',
    originalError: error
  };
}

/**
 * Check if an error is an ApiError
 */
function isApiError(error: unknown): error is ApiError {
  return (
    typeof error === 'object' &&
    error !== null &&
    'error' in error &&
    typeof (error as any).error === 'object' &&
    'message' in (error as any).error
  );
}

/**
 * Get user-friendly error messages for common error codes
 */
export function getUserFriendlyMessage(code: string): string {
  const messages: Record<string, string> = {
    'VALIDATION_ERROR': 'Please check the form for errors and try again.',
    'RESOURCE_NOT_FOUND': 'The requested item could not be found.',
    'NETWORK_ERROR': 'Unable to connect to the server. Please check your internet connection.',
    'UNAUTHORIZED': 'You need to sign in to access this feature.',
    'FORBIDDEN': 'You do not have permission to perform this action.',
    'INTERNAL_SERVER_ERROR': 'A server error occurred. Please try again later.',
    'TYPE_MISMATCH': 'Invalid data format. Please check your input.',
    'CONSTRAINT_VIOLATION': 'The data provided does not meet the requirements.',
    'BUSINESS_VALIDATION_ERROR': 'The operation could not be completed due to business rules.',
    'MALFORMED_REQUEST': 'Invalid request format. Please try again.',
  };

  return messages[code] || 'An unexpected error occurred. Please try again.';
}

/**
 * Log errors for debugging and monitoring
 */
export function logError(error: ParsedError, context?: string) {
  const logData = {
    code: error.code,
    message: error.message,
    details: error.details,
    context,
    timestamp: new Date().toISOString(),
    userAgent: navigator.userAgent,
    url: window.location.href
  };

  // In development, log to console
  if (import.meta.env.MODE === 'development') {
    console.error('Error logged:', logData, error.originalError);
  }

  // In production, you would typically send this to a logging service
  // Example: sendToLoggingService(logData);
}

/**
 * Check if an error should trigger a retry
 */
export function isRetryableError(code: string): boolean {
  const retryableCodes = [
    'NETWORK_ERROR',
    'INTERNAL_SERVER_ERROR',
    'TIMEOUT_ERROR'
  ];
  return retryableCodes.includes(code);
}

/**
 * Check if an error should redirect to login
 */
export function shouldRedirectToLogin(code: string): boolean {
  return code === 'UNAUTHORIZED' || code === '401';
}

/**
 * Extract validation errors from API response
 */
export function extractValidationErrors(error: ParsedError): Record<string, string> {
  if (error.code === 'VALIDATION_ERROR' && error.details) {
    if (typeof error.details === 'object' && !Array.isArray(error.details)) {
      return error.details as Record<string, string>;
    }
  }
  return {};
}