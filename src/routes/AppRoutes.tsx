import { Routes, Route } from "react-router-dom";
import HomePage from "../pages/HomePage";
import LoginComponent from "../features/auth/components/LoginComponent.tsx";
import RegisterComponent from "../features/auth/components/RegisterComponent.tsx";
import CatalogPage from "../features/book/pages/CatalogPage.tsx";
import BookDetailComponent from "../features/home/BookDetailComponent.tsx";
import CartPage from "../pages/CartPage";
import FavoriteBooksPage from "../pages/FavoriteBooksPage";
import OrdersPage from "../pages/OrderHistoryPage.tsx";
import ProfilePage from "../pages/ProfilePage.tsx";
import HelpPage from "../pages/HelpPage";

import AdminCatalogComponent from '../features/admin/components/AdminCatalogComponent.tsx';

import EditProductComponent from '../features/admin/components/EditProductComponent.tsx';

function CreateProductPage() {
        return null;
}

const AppRoutes = () => (
    <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginComponent />} />
        <Route path="/register" element={<RegisterComponent />} />
        <Route path="/catalog" element={<CatalogPage />} />
        <Route path="/book/:id" element={<BookDetailComponent />} />
        <Route path="/cart" element={<CartPage />} />
        <Route path="/favorites" element={<FavoriteBooksPage />} />
        <Route path="/orders" element={<OrdersPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/help" element={<HelpPage />} />
        <Route path="/admin/catalog" element={<AdminCatalogComponent />} />
        <Route path="/admin/edit/:id" element={<EditProductComponent />} />
        <Route path="/admin/create" element={<CreateProductPage />} />
    </Routes>
);

export default AppRoutes;
