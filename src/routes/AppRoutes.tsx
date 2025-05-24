import { Routes, Route, Navigate } from 'react-router-dom';
import type { JSX } from 'react';
import CatalogPage from "../features/book/pages/CatalogPage.tsx";
import LoginPage from "../features/user/pages/LoginPage.tsx";
import RegisterPage from "../features/user/pages/RegisterPage.tsx";
import ProfilePage from "../features/user/pages/ProfilePage.tsx";
import CartPage from "../features/order/pages/CartPage.tsx";
import FavoritesPage from "../features/book/pages/FavoritesPage.tsx";
import Returns from "../features/order/pages/ReturnsPage.tsx";
import OrderPage from "../features/order/pages/OrderPage.tsx";

import PrivateRoute from "./PrivateRoute.tsx";
import {Roles} from "../constants/Roles.ts";
import PublicRoute from "./PublicRoute.tsx";
import PaymentSuccess from "../features/order/components/PaymentSuccess.tsx";
import PaymentCancel from "../features/order/components/PaymentCancel.tsx";
import Logout from "../features/user/components/Logout.tsx";
import AdminLayout from "../components/AdminLayout.tsx";
import BooksPage from "../features/admin/pages/BooksPage.tsx";
import CreateBookPage from "../features/admin/pages/CreateBookPage.tsx";
import RecoverPasswordPage from "../features/user/pages/RecoverPasswordPage.tsx";
import OrderTrackingPage from "../features/order/pages/OrderTrackingPage.tsx";

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

            <Route path="/recover-password" element={
                <PublicRoute>
                    <RecoverPasswordPage />
                </PublicRoute>
            } />

            <Route path="/logout" element={
                <Logout />
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
            <Route path="/tracking/:orderId" element={
                <PrivateRoute roles={[Roles.CLIENT,Roles.ADMIN]}>
                    <OrderTrackingPage />
                </PrivateRoute>
            } />
            <Route path="/favorites" element={
                <PrivateRoute roles={[Roles.CLIENT,Roles.ADMIN]}>
                    <FavoritesPage />
                </PrivateRoute>
            } />
            <Route path="/returns" element={
                <PrivateRoute roles={[Roles.CLIENT,Roles.ADMIN]}>
                    <Returns />
                </PrivateRoute>
            } />
            <Route path="/orders" element={
                <PrivateRoute roles={[Roles.CLIENT,Roles.ADMIN]}>
                    <OrderPage />
                </PrivateRoute>
            } />

            <Route path="/admin" element={
                <PrivateRoute roles={[Roles.ADMIN]}>
                    <AdminLayout />
                </PrivateRoute>
            }>
                <Route path="" element={<BooksPage />} />
                <Route path="create-book" element={<CreateBookPage/>} />

                <Route path="*" element={<Navigate to="/admin" replace />} />
            </Route>


            {/* Fallback */}
            <Route path="*" element={<Navigate to="/" />} />
        </Routes>
    );
};

export default AppRoutes;
