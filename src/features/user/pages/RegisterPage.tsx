import React from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { VscMail } from 'react-icons/vsc';
import { FaEye } from 'react-icons/fa';

const RegisterPage = () => {
  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    navigate('/login');
  };

  return (
      <div className="flex h-screen">
        <div className="flex flex-col items-center justify-center w-1/2 bg-[#80AFAB] border-r-2 border-gray-300 p-8">
          <img src="/assets/logo.png" alt="Logo Get-A-Book" className="w-50 mb-6" />
          <h1 className="text-4xl font-extrabold text-gray-900">GET-A-BOOK</h1>
        </div>

        <div className="flex flex-col justify-center items-center w-1/2 bg-[#80AFAB] px-10">
          <h2 className="text-3xl font-extrabold mb-6 text-gray-900">Registro</h2>

          <form onSubmit={handleSubmit} className="w-full max-w-sm space-y-4">
            <div>
              <label className="block text-sm font-medium text-black">Correo</label>
              <div className="relative mt-1">
                <input
                    type="email"
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
                    type="password"
                    placeholder="Ingrese su contraseña"
                    required
                    className="bg-amber-100 text-black w-full px-4 py-2 pr-10 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-teal-500"
                />
                <span className="absolute inset-y-0 right-3 flex items-center text-gray-400">
                <FaEye className="text-black" />
              </span>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-black">Confirmar Contraseña</label>
              <div className="relative mt-1">
                <input
                    type="password"
                    placeholder="Confirme su contraseña"
                    required
                    className="bg-amber-100 text-black w-full px-4 py-2 pr-10 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-teal-500"
                />
                <span className="absolute inset-y-0 right-3 flex items-center text-gray-400">
                <FaEye className="text-black" />
              </span>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-black">Dirección</label>
              <input
                  type="text"
                  placeholder="Ingrese su dirección"
                  required
                  className="bg-amber-100 text-black w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-teal-500 mt-1"
              />
            </div>

            <button
                type="submit"
                className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-4 rounded transition"
            >
              Regístrate
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
