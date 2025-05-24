import { BrowserRouter, useLocation } from 'react-router-dom';
import './App.css';

import Navbar from './components/Navbar.tsx';
import AppRoutes from "./routes/AppRoutes.tsx";
import { useEffect } from 'react';
import {SearchProvider} from "./context/SearchContext.tsx";

function AppWrapper() {
    return (
        <BrowserRouter>
            <SearchProvider>
                <App />
            </SearchProvider>
        </BrowserRouter>
    );
}

function App() {
    const location = useLocation();
    const noNavbarRoutes = ['/login', '/register', "/recover-password"];
    const shouldShowNavbar = !noNavbarRoutes.some(path => location.pathname.startsWith(path)) &&
        !location.pathname.startsWith('/admin');

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [location]);

    return (
        <>
            {shouldShowNavbar && <Navbar />}
            <AppRoutes />
        </>
    );
}

export default AppWrapper;
