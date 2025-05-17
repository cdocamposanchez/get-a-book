import { Routes, Route, Navigate } from 'react-router-dom';
import ProfilePage from './pages/ProfilePage';
import LoginPage from "./pages/LoginPage";
import type {JSX} from "react";
import Orders from './pages/OrdersPage';
import Tracking from './pages/Tracking';
import Cart from './pages/Cart';
import Favorites from './pages/Favorites';
import Returns from './pages/Returns';

const AppRoutes = (): JSX.Element => {
    const isAuthenticated = localStorage.getItem('authToken');

    return (
        <Routes>
            {/* Ruta pública */}
            <Route path="/login" element={<LoginPage />} />  {/* Ruta protegida */}
            <Route path="/profile" element={isAuthenticated ? ( <ProfilePage />     ) :  <Navigate to="/login" />
            <Route path="/orders" element={<Orders />} />
            <Route path="/tracking" element={<Tracking />} />
            <Route path="/cart" element={<Cart />} />
            <Route path="/favorites" element={<Favorites />} />
            <Route path="/returns" element={<Returns />} />
                    )
                }
            />

            {/* Agregar rutas */}
        </Routes>
    );
};

export default AppRoutes;




