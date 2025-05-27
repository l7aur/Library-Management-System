import React from 'react';
import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { useAuth } from './config/GlobalState';
import {LOGIN_PATH, UNAUTHORIZED_PATH} from './constants/Paths';

interface ProtectedRouteProps {
    allowedRoles?: string[];
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ allowedRoles }) => {
    const { isAuthenticated, user } = useAuth();
    const location = useLocation();

    if (!isAuthenticated) {
        return <Navigate to={LOGIN_PATH} state={{ from: location }} />;
    }

    if (allowedRoles && user && user.role) {
        if (!allowedRoles.includes(user.role)) {
            return <Navigate to={UNAUTHORIZED_PATH} />;
        }
    }

    return <Outlet />;
};

export default ProtectedRoute;