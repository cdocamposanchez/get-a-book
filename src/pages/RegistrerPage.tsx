import { useState } from "react";
import { Link } from "react-router-dom";

const RegisterPage = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (password === confirmPassword) {
            // Implementación para registrar al usuario
        } else {
            alert("Las contraseñas no coinciden");
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 py-8 px-4">
            <div className="max-w-md mx-auto bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-3xl font-semibold text-center mb-6 text-gray-800">Crear Cuenta</h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label htmlFor="email" className="block text-gray-600">Correo Electrónico</label>
                        <input
                            type="email"
                            id="email"
                            className="w-full px-4 py-2 border rounded-md"
                            placeholder="Ingrese su correo"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="password" className="block text-gray-600">Contraseña</label>
                        <input
                            type="password"
                            id="password"
                            className="w-full px-4 py-2 border rounded-md"
                            placeholder="Ingrese su contraseña"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="confirmPassword" className="block text-gray-600">Confirmar Contraseña</label>
                        <input
                            type="password"
                            id="confirmPassword"
                            className="w-full px-4 py-2 border rounded-md"
                            placeholder="Confirme su contraseña"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
                    >
                        Crear Cuenta
                    </button>
                </form>
                <div className="mt-4 text-center">
                    <p className="text-sm text-gray-600">
                        ¿Ya tienes cuenta?{" "}
                        <Link to="/login" className="text-blue-600 hover:underline">
                            Inicia sesión
                        </Link>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default RegisterPage;
