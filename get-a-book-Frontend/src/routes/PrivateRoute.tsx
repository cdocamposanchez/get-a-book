import { Route, Navigate } from 'react-router-dom';

const PrivateRoute = ({ component: Component, ...rest }: any) => {
    const isAuthenticated = localStorage.getItem('authToken');

    return (
        <Route
            {...rest}
            element={isAuthenticated ? <Component /> : <Navigate to="/login" />}
        />
    );
};

export default PrivateRoute;

