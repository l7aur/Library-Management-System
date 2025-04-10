import { Navigate, Outlet } from 'react-router-dom';
import {useAuth} from "./config/globalState.tsx";

function ProtectedRoute() {
    const { isAuthenticated } = useAuth();

    if (!isAuthenticated) {
        // Redirect to the login page if not authenticated
        return <Navigate to="/login" />;
    }

    // If authenticated, render the child routes
    return <Outlet />;
}

export default ProtectedRoute;