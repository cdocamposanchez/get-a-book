import React from 'react';
import { useNavigate } from 'react-router-dom';
import './RegisterPage.css';

const RegisterPage = () => {
  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Aquí podrías validar los campos y guardar el usuario si lo conectas a un backend
    navigate('/login'); // Redirige al login después de registrarse
  };

  return (
    <div className="auth-container">
      <div className="auth-left">
        <img src="/logo.png" alt="Logo" className="auth-logo" />
        <h1>GET-A-BOOK</h1>
      </div>
      <div className="auth-right">
        <h2>Registro</h2>
        <form onSubmit={handleSubmit}>
          <label>Correo</label>
          <div className="input-container">
            <input type="email" placeholder="Ingrese su correo" required />
            <span className="icon">✉️</span>
          </div>

          <label>Contraseña</label>
          <div className="input-container">
            <input type="password" placeholder="Ingrese su contraseña" required />
            <span className="icon">🙈</span>
          </div>

          <label>Confirmar Contraseña</label>
          <div className="input-container">
            <input type="password" placeholder="Ingrese su contraseña" required />
            <span className="icon">🙈</span>
          </div>

          <label>Dirección</label>
          <div className="input-container">
            <input type="text" placeholder="Ingrese su Dirección" required />
          </div>

          <button type="submit" className="register-button">Regístrate</button>
        </form>
      </div>
    </div>
  );
};

export default RegisterPage;
