// authStore.js
import {useState, createContext, useContext, ReactNode} from 'react';
import {AppUserType} from "../types/AppUserType.tsx";
import * as React from "react";
import LoginResponseType from "../types/LoginResponseType.tsx";

// Create the Auth Context
const AuthContext = createContext<{
    isAuthenticated: boolean;
    user: AppUserType | null;
    token: string | null; // Add token to the context
    login: (loginResponse : LoginResponseType) => void;
    logout: () => void;
}>({
    isAuthenticated: false,
    user: null,
    token: null, // Initialize token in the context
    login: () => {},
    logout: () => {},
});

interface AuthProviderProps {
    children: ReactNode;
}

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

    const [token, setToken] = useState(() => {
        // Initialize token from localStorage
        const storedToken = localStorage.getItem('token');
        return storedToken || null;
    });

    // Function to log in
    const login = (loginResponse: LoginResponseType) => {
        setIsAuthenticated(true);
        console.log(loginResponse.appUser);
        if (loginResponse.appUser != null) {
            setUser(loginResponse.appUser);
            localStorage.setItem('user', JSON.stringify(loginResponse.appUser));
        } else {
            setUser(null);
        }
        setToken(loginResponse.token);
        localStorage.setItem('isAuthenticated', 'true');
        localStorage.setItem('token', loginResponse.token.toString());
    };

    // Function to log out
    const logout = () => {
        setIsAuthenticated(false);
        setUser(null);
        setToken(null);
        localStorage.removeItem('isAuthenticated');
        localStorage.removeItem('user');
        localStorage.removeItem('token');
        localStorage.removeItem('shoppingCart');
    };

    // Value passed to the context provider
    const authContextValue = {
        isAuthenticated,
        user,
        token,
        login,
        logout,
    };

    return (
        <AuthContext.Provider value={authContextValue}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    return useContext(AuthContext);
};