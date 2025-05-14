import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import './App.css';

import ProfilePage from './pages/ProfilePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import Orders from './pages/OrdersPage';
import Tracking from './pages/Tracking';
import Cart from './pages/Cart';
import Favorites from './pages/Favorites';
import Returns from './pages/Returns';
import Navbar from './components/Navbar';
import HomePage from './pages/HomePage';

function App() {
  const isAuthenticated = localStorage.getItem('authToken');

  return (
    <Router>
      <div>
        <Navbar />
        <Routes>
          {/* Redirige siempre a /home sin validar authToken */}
          <Route path="/" element={<Navigate to="/home" />} />

          {/* Rutas normales */}
          <Route path="/home" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/orders" element={<Orders />} />
          <Route path="/tracking" element={<Tracking />} />
          <Route path="/cart" element={<Cart />} />
          <Route path="/favorites" element={<Favorites />} />
          <Route path="/returns" element={<Returns />} />

          {/* Catch-all: redirige a / */}
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;