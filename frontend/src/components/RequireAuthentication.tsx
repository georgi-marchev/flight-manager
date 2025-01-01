import { useLocation, Navigate, Outlet } from "react-router-dom";
import useAuth from "../hooks/useAuth";

const RequireAuthentication = () => {
    const { auth } = useAuth();
    const location = useLocation();

    const isLoggedIn = auth && auth.accessToken;

    return (
        isLoggedIn
            ? <Outlet />
            : <Navigate to="/login" state={{ from: location }} replace />
    );
}

export default RequireAuthentication;