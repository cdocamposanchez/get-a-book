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
import {Roles} from "../constants/Roles.ts";
import PublicRoute from "./PublicRoute.tsx";
import PaymentSuccess from "../features/order/components/PaymentSuccess.tsx";
import PaymentCancel from "../features/order/components/PaymentCancel.tsx";

const AppRoutes = (): JSX.Element => {
    return (
        <Routes>
            <Route path="/" element={<Navigate to="/home" />} />

            {/* Públicas */}

            <Route path="/login" element={
                <PublicRoute>
                    <LoginPage />
                </PublicRoute>
            } />

            <Route path="/register" element={
                <PublicRoute>
                    <RegisterPage />
                </PublicRoute>
            } />

            <Route path="/home" element={<CatalogPage />} />
            <Route path="/cart" element={<CartPage />} />
            <Route path="/payment/success" element={<PaymentSuccess />} />
            <Route path="/payment/cancel" element={<PaymentCancel />} />

            {/* Rutas para usuarios autenticados (cliente) */}
            <Route path="/profile" element={
                <PrivateRoute roles={[Roles.CLIENT,Roles.ADMIN ]}>
                    <ProfilePage />
                </PrivateRoute>
            } />
            <Route path="/tracking" element={
                <PrivateRoute roles={[Roles.CLIENT]}>
                    <TrackingPage />
                </PrivateRoute>
            } />
            <Route path="/favorites" element={
                <PrivateRoute roles={[Roles.CLIENT]}>
                    <FavoritesPage />
                </PrivateRoute>
            } />
            <Route path="/returns" element={
                <PrivateRoute roles={[Roles.CLIENT]}>
                    <Returns />
                </PrivateRoute>
            } />
            <Route path="/orders" element={
                <PrivateRoute roles={[Roles.CLIENT]}>
                    <OrderPage />
                </PrivateRoute>
            } />

            {/* Rutas exclusivas para administradores */}
            {/*<Route path="/admin/dashboard" element={*/}
            {/*    <PrivateRoute roles={[Roles.ADMIN]}>*/}
            {/*        <AdminDashboard />*/}
            {/*    </PrivateRoute>*/}
            {/*} />*/}
            {/*<Route path="/admin/users" element={*/}
            {/*    <PrivateRoute roles={[Roles.ADMIN]}>*/}
            {/*        <ManageUsers />*/}
            {/*    </PrivateRoute>*/}
            {/*} />*/}

            {/* Fallback */}
            <Route path="*" element={<Navigate to="/" />} />
        </Routes>
    );
};

export default AppRoutes;
