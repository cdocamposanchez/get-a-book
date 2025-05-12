import { Routes, Route, Navigate } from 'react-router-dom';
import ProfilePage from './pages/ProfilePage';
import LoginPage from "./pages/LoginPage";
import type {JSX} from "react";

const AppRoutes = (): JSX.Element => {
    const isAuthenticated = localStorage.getItem('authToken');

    return (
        <Routes>
            {/* Ruta pública */}
            <Route path="/login" element={<LoginPage />} />

            {/* Ruta protegida */}
            <Route
                path="/profile"
                element={
                    isAuthenticated ? (
                        <ProfilePage />
                    ) : (
                        <Navigate to="/login" />
                    )
                }
            />

            {/* Agregar rutas */}
        </Routes>
    );
};

export default AppRoutes;




