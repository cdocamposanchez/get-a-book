import { Routes, Route, Navigate } from 'react-router-dom';
import type { JSX } from 'react';
import CatalogPage from "../features/book/pages/CatalogPage.tsx";
import LoginPage from "../features/user/pages/LoginPage.tsx";
import RegisterPage from "../features/user/pages/RegisterPage.tsx";
import ProfilePage from "../features/user/pages/ProfilePage.tsx";
import TrackingPage from "../features/order/pages/TrackingPage.tsx";
import CartPage from "../features/order/pages/CartPage.tsx";
import FavoritesPage from "../features/book/pages/FavoritesPage.tsx";
import Returns from "../features/order/pages/ReturnsPage.tsx";
import OrderPage from "../features/order/pages/OrderPage.tsx";

import PrivateRoute from "./PrivateRoute.tsx";

const AppRoutes = (): JSX.Element => {
    return (
        <Routes>
            <Route path="/" element={<Navigate to="/home" />} />

            {/* Públicas */}
            <Route path="/home" element={<CatalogPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/cart" element={<CartPage />} />

            {/* Rutas para usuarios autenticados (cliente) */}
            <Route path="/profile" element={
                <PrivateRoute roles={['client', 'admin']}>
                    <ProfilePage />
                </PrivateRoute>
            } />
            <Route path="/tracking" element={
                <PrivateRoute roles={['client']}>
                    <TrackingPage />
                </PrivateRoute>
            } />
            <Route path="/favorites" element={
                <PrivateRoute roles={['client']}>
                    <FavoritesPage />
                </PrivateRoute>
            } />
            <Route path="/returns" element={
                <PrivateRoute roles={['client']}>
                    <Returns />
                </PrivateRoute>
            } />
            <Route path="/orders" element={
                <PrivateRoute roles={['client']}>
                    <OrderPage />
                </PrivateRoute>
            } />

            {/* Rutas exclusivas para administradores */}
            {/*<Route path="/admin/dashboard" element={*/}
            {/*    <PrivateRoute roles={['admin']}>*/}
            {/*        <AdminDashboard />*/}
            {/*    </PrivateRoute>*/}
            {/*} />*/}
            {/*<Route path="/admin/users" element={*/}
            {/*    <PrivateRoute roles={['admin']}>*/}
            {/*        <ManageUsers />*/}
            {/*    </PrivateRoute>*/}
            {/*} />*/}

            {/* Fallback */}
            <Route path="*" element={<Navigate to="/" />} />
        </Routes>
    );
};

export default AppRoutes;
