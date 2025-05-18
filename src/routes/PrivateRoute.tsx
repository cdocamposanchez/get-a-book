import { Navigate } from "react-router-dom";
import type {ReactNode} from "react";

const getAuth = () => {
    const data = localStorage.getItem("auth");
    return data ? JSON.parse(data) : null;
};

interface PrivateRouteProps {
    children: ReactNode;
    roles?: string[];
}

const PrivateRoute = ({ children, roles }: PrivateRouteProps) => {
    const auth = getAuth();

    if (!auth?.token) {
        return <Navigate to="/login" />;
    }

    if (roles && !roles.includes(auth.userRole)) {
        return <Navigate to="/home" />;
    }

    return <>{children}</>;
};

export default PrivateRoute;
