//import { Routes, Route } from 'react-router-dom';
import HomePage from '../pages/HomePage';
import LoginComponent from '../features/auth/LoginComponent';
import RegisterComponent from '../features/auth/RegisterComponent';
import AdminPage from '../features/admin/pages/AdminPage';

const AppRoutes = (): JSX.Element => {
        return (
            <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={<LoginComponent />} />
                    <Route path="/register" element={<RegisterComponent />} />
                    <Route path="/admin/*" element={<AdminPage />} />
            </Routes>
        );
};

export default AppRoutes;

