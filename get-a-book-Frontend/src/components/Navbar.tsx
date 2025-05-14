// src/components/Navbar.tsx
import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
  return (
    <div className="navbar">
      <div className="navbar-left">
        <Link to="/home">
          <img src="/logo.png" alt="Get-A-Book Logo" className="logo" />
        </Link>
      </div>
      <input type="text" placeholder="¿Qué estás buscando?" className="search-bar" />
      <div className="navbar-right">
        <Link to="/favorites" className="nav-icon">❤️</Link>
        <Link to="/cart" className="nav-icon">🛒</Link>
        <Link to="/returns" className="nav-icon">🔁</Link>
        <Link to="/orders" className="nav-icon">📦</Link> {/* Ícono de órdenes */}
        <Link to="/profile" className="nav-icon">👤</Link>
      </div>
    </div>
  );
};

export default Navbar;
