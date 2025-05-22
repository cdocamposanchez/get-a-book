import { Navigate } from "react-router-dom";
import type { ReactNode } from "react";

const getAuth = () => {
    const data = localStorage.getItem("auth");
    return data ? JSON.parse(data) : null;
};

interface PublicRouteProps {
    children: ReactNode;
}

const PublicRoute = ({ children }: PublicRouteProps) => {
    const auth = getAuth();

    if (auth?.token) {
        return <Navigate to="/home" />;
    }

    return <>{children}</>;
};

export default PublicRoute;
