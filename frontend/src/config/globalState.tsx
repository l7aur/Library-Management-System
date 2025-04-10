// authStore.js
import {useState, createContext, useContext, ReactNode} from 'react';
import {AppUserType} from "../types/AppUserType.tsx";
import * as React from "react";

// Create the Auth Context
const AuthContext = createContext<{
    isAuthenticated: boolean;
    user: AppUserType | null;
    login: (userData : AppUserType) => void;
    logout: () => void;
}>({
    isAuthenticated: false,
    user: null,
    login: () => {}, // Removed unused userData here
    logout: () => {},       // Removed unused userData here (it doesn't have one anyway)
});

interface AuthProviderProps {
    children: ReactNode;
}

// Auth Provider Component
export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(() => {
        // Initialize from localStorage
        const storedAuth = localStorage.getItem('isAuthenticated');
        return storedAuth === 'true';
    });

    const [user, setUser] = useState(() => {
        // Initialize user data from localStorage (if you store it)
        const storedUser = localStorage.getItem('user');
        return storedUser ? JSON.parse(storedUser) : null;
    });

    // Function to log in
    const login = (userData: AppUserType) => {
        setIsAuthenticated(true);
        setUser(userData);
        localStorage.setItem('isAuthenticated', 'true');
        localStorage.setItem('user', JSON.stringify(userData));
    };

    // Function to log out
    const logout = () => {
        setIsAuthenticated(false);
        setUser(null);
        localStorage.removeItem('isAuthenticated');
        localStorage.removeItem('user');
    };

    // Value passed to the context provider
    const authContextValue = {
        isAuthenticated,
        user,
        login,
        logout,
    };

    return (
        <AuthContext.Provider value={authContextValue}>
            {children}
        </AuthContext.Provider>
    );
};

// Custom hook to use the auth context
export const useAuth = () => {
    return useContext(AuthContext);
};