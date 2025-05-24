import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { VscMail } from 'react-icons/vsc';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import { userService } from '../UserService.ts';
import type { Register } from '../../../types/auth/register';

const RegisterPage = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState<Register>({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  });
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const validateEmail = (email: string): boolean => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    if (!validateEmail(form.email)) {
      setError('Formato de correo inválido');
      return;
    }

    if (form.password.length < 8) {
      setError('La contraseña debe tener al menos 8 caracteres');
      return;
    }

    if (form.password !== confirmPassword) {
      setError('Las contraseñas no coinciden');
      return;
    }

    setLoading(true);
    try {
      await userService.register(form);
      navigate('/home');
    } catch (err: any) {
      const response = err.response?.data;
      if (response?.data?.includes('already a user registered with the email')) {
        setError('Ese correo ya está registrado');
      } else {
        setError(response?.message ?? 'Error al registrarse');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
      <div className="flex h-screen">
        <div className="flex flex-col items-center justify-center w-1/2 bg-[#80AFAB] border-r-2 border-gray-300 p-8">
          <Link className="hover:scale-105 transition-transform" to="/home">
            <img src="/assets/logo.png" alt="Logo Get-A-Book" className="w-50 mb-6"/>
            <h1 className="text-4xl font-extrabold text-gray-900">GET-A-BOOK</h1>
          </Link>
        </div>

        <div className="flex flex-col justify-center items-center w-1/2 bg-[#80AFAB] px-10">
          <h2 className="text-3xl font-extrabold mb-6 text-gray-900">Registro</h2>

          <form onSubmit={handleSubmit} className="w-full max-w-sm space-y-4">
            <div>
              <label className="block text-sm font-medium text-black">Correo</label>
              <div className="relative mt-1">
                <input
                    type="email"
                    name="email"
                    value={form.email}
                    onChange={handleChange}
                    placeholder="Ingrese su correo"
                    required
                    className="bg-amber-100 text-black w-full px-4 py-2 pr-10 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-teal-500"
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
                    name="password"
                    value={form.password}
                    onChange={handleChange}
                    placeholder="Ingrese su contraseña"
                    required
                    className="bg-amber-100 text-black w-full px-4 py-2 pr-10 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-teal-500"
                />
                <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute inset-y-0 right-3 flex items-center text-gray-400 hover:scale-120 transition-transform"
                    aria-label={showPassword ? "Ocultar contraseña" : "Mostrar contraseña"}
                >
                  {showPassword ? <FaEye className="text-black"/> : <FaEyeSlash className="text-black"/>}
                </button>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-black">Confirmar Contraseña</label>
              <div className="relative mt-1">
                <input
                    type={showConfirmPassword ? "text" : "password"}
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    placeholder="Confirme su contraseña"
                    required
                    className="bg-amber-100 text-black w-full px-4 py-2 pr-10 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-teal-500"
                />
                <button
                    type="button"
                    onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                    className="absolute inset-y-0 right-3 flex items-center text-gray-400 hover:scale-120 transition-transform"
                    aria-label={showConfirmPassword ? "Ocultar contraseña" : "Mostrar contraseña"}
                >
                  {showConfirmPassword ? <FaEye className="text-black"/> : <FaEyeSlash className="text-black"/>}
                </button>
              </div>
            </div>


            <div>
              <label className="block text-sm font-medium text-black">Primer Nombre</label>
              <input
                  type="text"
                  name="firstName"
                  value={form.firstName}
                  onChange={handleChange}
                  placeholder="Ingrese su primer nombre"
                  required
                  className="bg-amber-100 text-black w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none
                  focus:ring-2 focus:ring-teal-500 mt-1"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-black">Primer Apellido</label>
              <input
                  type="text"
                  name="lastName"
                  value={form.lastName}
                  onChange={handleChange}
                  placeholder="Ingrese su primer apellido"
                  required
                  className="bg-amber-100 text-black w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none
                  focus:ring-2 focus:ring-teal-500 mt-1"
              />
            </div>

            {error && (
                <div className="text-red-600 text-sm font-semibold">
                  {error}
                </div>
            )}

            <button
                type="submit"
                disabled={loading}
                className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-4 rounded
                disabled:opacity-50 hover:scale-105 transition-transform border-black border-1"
            >
              {loading ? 'Registrando...' : 'Regístrate'}
            </button>

            <p className="text-sm text-center text-gray-700">
              ¿Ya tienes una cuenta?{' '}
              <Link to="/login" className="text-indigo-700 hover:underline">
                Inicia sesión
              </Link>
            </p>
          </form>
        </div>
      </div>
  );
};

export default RegisterPage;
