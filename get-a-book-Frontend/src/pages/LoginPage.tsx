import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './LoginPage.css';

const LoginPage = () => {
  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Simula autenticación y guarda token
    localStorage.setItem('authToken', 'demo-token');
    navigate('/profile');
  };

  return (
    <div className="auth-container">
      <div className="auth-left">
        <img src="/logo.png" alt="Logo Get-A-Book" className="auth-logo" />
        <h1>GET-A-BOOK</h1>
      </div>
      <div className="auth-right">
        <h2>Inicia Sesión</h2>
        <form onSubmit={handleSubmit}>
          <label>Correo</label>
          <div className="input-container">
            <input type="email" placeholder="Ingrese su correo" required />
            <span className="icon">📧</span>
          </div>

          <label>Contraseña</label>
          <div className="input-container">
            <input type="password" placeholder="Ingrese su contraseña" required />
            <span className="icon">🙈</span>
          </div>

          <a href="#" className="forgot-password">Olvidé mi contraseña</a>

          <button type="submit" className="login-button">Inicia sesión</button>

          <p className="register-link">
            ¿No tienes una cuenta? <Link to="/register">Regístrate</Link>
          </p>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
