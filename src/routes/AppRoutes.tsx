import { Routes, Route, Navigate } from 'react-router-dom';
import type {JSX} from "react";
import CatalogPage from "../features/book/pages/CatalogPage.tsx";
import LoginPage from "../features/user/pages/LoginPage.tsx";
import RegisterPage from "../features/user/pages/RegisterPage.tsx";
import ProfilePage from "../features/user/pages/ProfilePage.tsx";
import TrackingPage from "../features/order/pages/TrackingPage.tsx";
import CartPage from "../features/order/pages/CartPage.tsx";
import FavoritesPage from "../features/book/pages/FavoritesPage.tsx";
import Returns from "../features/order/pages/ReturnsPage.tsx";
import OrderPage from "../features/order/pages/OrderPage.tsx";



const AppRoutes = (): JSX.Element => {
    return (
        <Routes>
            {/* Redirige siempre a /home sin validar authToken */}
            <Route path="/" element={<Navigate to="/home" />} />

            {/* Rutas normales */}
            <Route path="/home" element={<CatalogPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/tracking" element={<TrackingPage />} />
            <Route path="/cart" element={<CartPage />} />
            <Route path="/favorites" element={<FavoritesPage />} />
            <Route path="/returns" element={<Returns />} />
            <Route path="/orders" element={<OrderPage />} />

            {/* Catch-all: redirige a / */}
            <Route path="*" element={<Navigate to="/" />} />
        </Routes>
    );
};

export default AppRoutes;




