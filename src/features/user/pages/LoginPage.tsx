import React, { useState } from 'react';
import { VscMail } from 'react-icons/vsc';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { userService } from '../UserService.ts';
import type { Login } from "../../../types/auth/login";

const LoginPage = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const queryParams = new URLSearchParams(location.search);
  const redirectPath = queryParams.get("redirect") || "/home";

  const validateEmail = (email: string): boolean => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    if (!validateEmail(email)) {
      setError('Formato de correo inválido');
      return;
    }

    setLoading(true);
    const credentials: Login = { email, password };

    try {
      await userService.login(credentials);
      navigate(redirectPath);
    } catch (err: any) {
      const response = err.response?.data;

      if (response?.data === "Bad credentials") {
        setError("Usuario o Contraseña Invalidos");
      } else {
        setError(response?.message ?? "Error al iniciar sesión");
      }
    } finally {
      setLoading(false);
    }
  }

  return (
      <div className="flex h-screen">
        <div className="flex flex-col items-center justify-center w-1/2 bg-[#80AFAB] border-r-2 border-gray-300 p-8">
          <Link className="hover:scale-105 transition-transform" to="/home">
            <img src="/assets/logo.png" alt="Logo Get-A-Book" className="w-50 mb-6"/>
            <h1 className="text-4xl font-extrabold text-gray-900">GET-A-BOOK</h1>
          </Link>

        </div>

        <div className="flex flex-col justify-center items-center w-1/2 bg-[#80AFAB] px-10">
          <h2 className="text-3xl font-extrabold mb-6 text-gray-900">Inicia Sesión</h2>

          <form onSubmit={handleSubmit} className="w-full max-w-sm space-y-4">
            <div>
              <label className="block text-sm font-medium text-black">Correo</label>
              <div className="relative mt-1">
                <input
                    type="email"
                    placeholder="Ingrese su correo"
                    required
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="bg-amber-100 text-black w-full px-4 py-2 pr-10 border border-gray-300 rounded-md
                    focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-teal-500"
                />
                <span className="absolute inset-y-0 right-3 flex items-center text-gray-400">
                  <VscMail className="text-black" />
                </span>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-black">Contraseña</label>
              <div className="relative mt-1">
                <input
                    type={showPassword ? "text" : "password"}
                    placeholder="Ingrese su contraseña"
                    required
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="bg-amber-100 text-black w-full px-4 py-2 pr-10 border border-gray-300 rounded-md
                     focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-teal-500"
                />
                <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute inset-y-0 right-3 flex items-center text-gray-400 hover:scale-120 transition-transform"
                    aria-label={showPassword ? "Ocultar contraseña" : "Mostrar contraseña"}
                >
                  {showPassword ? (
                      <FaEye className="text-black"/>
                  ) : (
                      <FaEyeSlash className="text-black"/>
                  )}
                </button>
              </div>

            </div>

            {error && (
                <div className="text-red-600 text-sm font-semibold">
                  {error}
                </div>
            )}

            <div className="text-right text-sm">
              <Link to="/recover-password" className="text-indigo-700 hover:underline">
                Olvidé mi contraseña
              </Link>
            </div>

            <button
                type="submit"
                disabled={loading}
                className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-4 rounded
                disabled:opacity-50 hover:scale-105 transition-transform border-black border-1"
            >
              {loading ? 'Cargando...' : 'Inicia sesión'}
            </button>

            <p className="text-sm text-center text-gray-700">
              ¿No tienes una cuenta?{' '}
              <Link to="/register" className="text-indigo-700 hover:underline">
                Regístrate
              </Link>
            </p>
          </form>
        </div>
      </div>
  );
};

export default LoginPage;
