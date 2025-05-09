import { Link } from "react-router-dom";

const HomePage = () => {
    return (
        <div className="min-h-screen bg-gray-100 py-8 px-4">
            <div className="max-w-lg mx-auto bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-4xl font-semibold text-center text-gray-800 mb-6">
                    Bienvenido a GET-A-BOOK
                </h2>
                <p className="text-lg text-gray-600 text-center mb-6">
                    Encuentra los mejores libros a precios increíbles.
                </p>
                <div className="text-center">
                    <Link
                        to="/catalog"
                        className="inline-block px-6 py-2 bg-blue-600 text-white rounded-full hover:bg-blue-700 transition"
                    >
                        Ver Catálogo
                    </Link>
                    <p className="mt-6 text-sm text-gray-600">
                        ¿Ya tienes una cuenta?{" "}
                        <Link to="/login" className="text-blue-600 hover:underline">
                            Inicia sesión
                        </Link>
                    </p>
                    <p className="text-sm text-gray-600">
                        ¿Nuevo aquí?{" "}
                        <Link to="/register" className="text-blue-600 hover:underline">
                            Regístrate
                        </Link>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default HomePage;
