import { Navigate } from "react-router-dom";
import type {ReactNode} from "react";
import {useAuth} from "../features/user/hooks/useAuth.ts";


interface PrivateRouteProps {
    children: ReactNode;
    roles?: string[];
}

const PrivateRoute = ({ children, roles }: PrivateRouteProps) => {
    const { token, userRole } = useAuth();

    if (!token) {
        return <Navigate to="/login" />;
    }

    if (roles && !roles.includes(userRole)) {
        return <Navigate to="/home" />;
    }

    return <>{children}</>;
};

export default PrivateRoute;
