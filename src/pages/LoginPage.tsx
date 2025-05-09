import { Link } from "react-router-dom";

const LoginPage = () => {
    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
            <div className="w-full max-w-md bg-white p-8 rounded-2xl shadow-lg">
                <h2 className="text-2xl font-bold text-center mb-6 text-gray-800">Iniciar Sesión</h2>

                <form>
                    <div className="mb-4">
                        <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">
                            Correo electrónico
                        </label>
                        <input
                            type="email"
                            id="email"
                            className="w-full px-4 py-2 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
                            placeholder="ejemplo@correo.com"
                            required
                        />
                    </div>

                    <div className="mb-4">
                        <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">
                            Contraseña
                        </label>
                        <input
                            type="password"
                            id="password"
                            className="w-full px-4 py-2 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
                            placeholder="********"
                            required
                        />
                    </div>

                    <div className="flex items-center justify-between mb-6">
                        <Link to="/forgot-password" className="text-sm text-blue-600 hover:underline">
                            ¿Olvidaste tu contraseña?
                        </Link>
                    </div>

                    <button
                        type="submit"
                        className="w-full bg-blue-600 text-white py-2 rounded-xl hover:bg-blue-700 transition duration-300"
                    >
                        Iniciar Sesión
                    </button>
                </form>

                <p className="mt-6 text-center text-sm text-gray-600">
                    ¿No tienes una cuenta?{" "}
                    <Link to="/register" className="text-blue-600 hover:underline">
                        Regístrate
                    </Link>
                </p>
            </div>
        </div>
    );
};

export default LoginPage;

