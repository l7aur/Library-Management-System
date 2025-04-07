import React, { createContext, useContext, useReducer, Dispatch, ReactNode, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { LOGIN_PATH } from '../constants/Paths';
import {AppUserType} from "../types/AppUserType.tsx";

// 1. Define the State Type
interface AuthState {
    isAuthenticated: boolean;
    user: AppUserType | null;
    loading: boolean;
    error: string | null;
}

// 2. Define Action Types
type AuthAction =
    | { type: 'LOGIN_SUCCESS'; payload: AppUserType }
    | { type: 'LOGOUT' }
    | { type: 'AUTH_REQUEST' }
    | { type: 'AUTH_FAILURE'; payload: string | null }
    | { type: 'SET_AUTH_FROM_STORAGE'; payload: { isAuthenticated: boolean; user: AppUserType | null } }; // For initial load

// 3. Define the Reducer Function
const authReducer = (state: AuthState, action: AuthAction): AuthState => {
    switch (action.type) {
        case 'AUTH_REQUEST':
            return { ...state, loading: true, error: null };
        case 'LOGIN_SUCCESS':
            return { ...state, isAuthenticated: true, user: action.payload, loading: false, error: null };
        case 'LOGOUT':
            return { ...state, isAuthenticated: false, user: null, loading: false, error: null };
        case 'AUTH_FAILURE':
            return { ...state, isAuthenticated: false, user: null, loading: false, error: action.payload };
        case 'SET_AUTH_FROM_STORAGE':
            return { ...state, isAuthenticated: action.payload.isAuthenticated, user: action.payload.user, loading: false, error: null };
        default:
            return state;
    }
};

// 4. Create the Contexts
interface AuthStateContextProps {
    state: AuthState;
    dispatch: Dispatch<AuthAction>;
}
const AuthStateContext = createContext<AuthStateContextProps | undefined>(undefined);

// 5. Create the Provider Component
interface AuthProviderProps {
    children: ReactNode;
}
export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    // In AuthProvider
    const [state, dispatch] = useReducer(authReducer, {
        isAuthenticated: false,
        user: null,
        loading: false,
        error: null,
    });
    console.log('AuthProvider initial state:', state);

    useEffect(() => {
        const storedAuth = localStorage.getItem('auth');
        console.log('AuthProvider - Retrieved from localStorage:', storedAuth);
        if (storedAuth) {
            try {
                const parsedAuth = JSON.parse(storedAuth);
                console.log('AuthProvider - Parsed auth:', parsedAuth);
                console.log('AuthProvider - Dispatching SET_AUTH_FROM_STORAGE', parsedAuth);
                dispatch({ type: 'SET_AUTH_FROM_STORAGE', payload: parsedAuth });
            } catch (error) {
                console.error('AuthProvider - Error parsing auth from storage:', error);
                localStorage.removeItem('auth');
            }
        }
    }, []);

    useEffect(() => {
        console.log('AuthProvider - State updated:', state);
        localStorage.setItem('auth', JSON.stringify(state));
    }, [state]);

    return (
        <AuthStateContext.Provider value={{ state, dispatch }}>
            {children}
        </AuthStateContext.Provider>
    );
};

// 6. Create a Custom Hook to Use the Context
export const useAuth = () => {
    const context = useContext(AuthStateContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};

// 7. Create a ProtectedRoute Component
interface ProtectedRouteProps {
    children: ReactNode;
}
export const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
    const { state } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (!state.isAuthenticated && !state.loading) {
            navigate(LOGIN_PATH);
        }
    }, [state.isAuthenticated, state.loading, navigate]);

    if (!state.isAuthenticated && !state.loading) {
        return null;
    }

    return <>{children}</>;
};