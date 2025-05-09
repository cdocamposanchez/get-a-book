import { Routes, Route } from "react-router-dom";
import HomePage from "../pages/HomePage";
import LoginPage from "../pages/LoginPage";
import RegisterPage from "../pages/RegistrerPage.tsx";
import CatalogPage from "../pages/CatalogoPage.tsx";
import BookDetailPage from "../pages/BookDetailPage";
import CartPage from "../pages/CartPage";
import FavoriteBooksPage from "../pages/FavoriteBooksPage";
import OrdersPage from "../pages/OrderPage.tsx";
import ProfilePage from "../pages/ProfilePage.tsx";
import HelpPage from "../pages/HelpPage";

// Rutas admin
import AdminCatalogPage from '../pages/Admin/AdminCatalogPage.tsx';

import EditProductPage from '../pages/Admin/EditProductPage.tsx';

function CreateProductPage() {
        return null;
}

const AppRoutes = () => (
    <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/catalog" element={<CatalogPage />} />
        <Route path="/book/:id" element={<BookDetailPage />} />
        <Route path="/cart" element={<CartPage />} />
        <Route path="/favorites" element={<FavoriteBooksPage />} />
        <Route path="/orders" element={<OrdersPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/help" element={<HelpPage />} />
        <Route path="/admin/catalog" element={<AdminCatalogPage />} />
        <Route path="/admin/edit/:id" element={<EditProductPage />} />
        <Route path="/admin/create" element={<CreateProductPage />} />
    </Routes>
);

export default AppRoutes;
