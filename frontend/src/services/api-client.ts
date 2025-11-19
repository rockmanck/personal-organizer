import {ApiError} from '../types';
import { parseError, logError } from '../utils/error-handling';

export class ApiClient {
  private readonly baseURL: string;

  constructor(baseURL: string = import.meta.env.VITE_API_BASE_URL ? `${import.meta.env.VITE_API_BASE_URL}` : '/api/v1') {
    this.baseURL = baseURL;
  }

  private async handleResponse<T>(response: Response): Promise<T> {
    if (!response.ok) {
      let errorData: ApiError;

      try {
        errorData = await response.json();
      } catch (jsonParseError) {
        // Fallback for non-JSON error responses
        console.warn('Failed to parse error response as JSON:', jsonParseError);
        errorData = {
          error: {
            code: 'HTTP_ERROR',
            message: `HTTP ${response.status}: ${response.statusText}`,
            timestamp: new Date().toISOString(),
            path: response.url,
            details: {
              status: response.status,
              statusText: response.statusText
            }
          }
        };
      }

      // Log the error for monitoring
      const parsedError = parseError(errorData);
      logError(parsedError, `API Call: ${response.url}`);

      // Throw the error for handling by the caller
      throw errorData;
    }

    if (response.status === 204) {
      return {} as T;
    }

    try {
      return await response.json();
    } catch (jsonParseError) {
      // Log parsing errors
      const error = parseError(jsonParseError);
      logError(error, `JSON Parse Error: ${response.url}`);
      throw jsonParseError;
    }
  }

  private async makeRequest<T>(
    method: string,
    endpoint: string,
    data?: any,
    options: RequestInit = {}
  ): Promise<T> {
    const url = `${this.baseURL}${endpoint}`;

    const config: RequestInit = {
      method,
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    };

    if (data) {
      config.body = JSON.stringify(data);
    }

    try {
      const response = await fetch(url, config);
      return await this.handleResponse<T>(response);
    } catch (error) {
      // Handle network errors
      if (error instanceof TypeError && error.message.includes('fetch')) {
        const networkError = {
          error: {
            code: 'NETWORK_ERROR',
            message: 'Unable to connect to the server',
            timestamp: new Date().toISOString(),
            path: url,
            details: { originalError: error.message }
          }
        };
        logError(parseError(networkError), `Network Error: ${url}`);
        throw networkError;
      }

      // Re-throw other errors (like ApiErrors)
      throw error;
    }
  }

  async get<T>(endpoint: string, options?: RequestInit): Promise<T> {
    return this.makeRequest<T>('GET', endpoint, undefined, options);
  }

  async post<T>(endpoint: string, data?: any, options?: RequestInit): Promise<T> {
    return this.makeRequest<T>('POST', endpoint, data, options);
  }

  async put<T>(endpoint: string, data?: any, options?: RequestInit): Promise<T> {
    return this.makeRequest<T>('PUT', endpoint, data, options);
  }

  async patch<T>(endpoint: string, data?: any, options?: RequestInit): Promise<T> {
    return this.makeRequest<T>('PATCH', endpoint, data, options);
  }

  async delete<T>(endpoint: string, options?: RequestInit): Promise<T> {
    return this.makeRequest<T>('DELETE', endpoint, undefined, options);
  }
}

export const apiClient = new ApiClient();
