import { useLocation, Navigate, Outlet } from "react-router-dom";
import useAuth from "../hooks/useAuth";

const RequireAuthorization = ({ allowedRoles }) => {
    const { auth } = useAuth();
    const location = useLocation();

    const isAuthorized = Boolean(auth.authorities && auth.authorities.find(role => allowedRoles.includes(role)));

    return (
        isAuthorized
            ? <Outlet />
            : <Navigate to="/unauthorized" state={{ from: location }} replace />
    );
}

export default RequireAuthorization;