import { Routes, Route, Navigate } from 'react-router-dom';
import type {JSX} from "react";
import HomePage from "../pages/HomePage.tsx";
import LoginPage from "../pages/LoginPage.tsx";
import RegisterPage from "../pages/RegisterPage.tsx";
import ProfilePage from "../pages/ProfilePage.tsx";
import Tracking from "../pages/Tracking.tsx";
import CartPage from "../features/order/pages/CartPage.tsx";
import Favorites from "../pages/Favorites.tsx";
import Returns from "../pages/Returns.tsx";



const AppRoutes = (): JSX.Element => {
    return (
        <Routes>
            {/* Redirige siempre a /home sin validar authToken */}
            <Route path="/" element={<Navigate to="/home" />} />

            {/* Rutas normales */}
            <Route path="/home" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/tracking" element={<Tracking />} />
            <Route path="/cart" element={<CartPage />} />
            <Route path="/favorites" element={<Favorites />} />
            <Route path="/returns" element={<Returns />} />

            {/* Catch-all: redirige a / */}
            <Route path="*" element={<Navigate to="/" />} />
        </Routes>
    );
};

export default AppRoutes;




