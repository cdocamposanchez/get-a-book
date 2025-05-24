import { useState } from 'react';
import { userService } from '../UserService';
import {Link, useNavigate} from 'react-router-dom';

const RecoverPasswordPage = () => {
    const navigate = useNavigate();

    const [step, setStep] = useState<1 | 2 | 3>(1);
    const [email, setEmail] = useState('');
    const [code, setCode] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [message, setMessage] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    const validateEmail = (email: string): boolean => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

    const handleSendCode = async () => {
        if (!validateEmail(email)) {
            setError("Correo inválido");
            return;
        }

        setError(null);
        setLoading(true);
        try {
            const msg = await userService.sendRecoveryCode(email);
            setMessage(msg);
            setStep(2);
        } catch (err: any) {
            const errorMsg = err.response?.data?.data ?? err.response?.data?.message ?? "Error al enviar el código";
            if (typeof errorMsg === "string" && errorMsg.includes("No user found with email")) {
                setError("Correo no registrado");
            } else {
                setError(errorMsg);
            }
        } finally {
            setLoading(false);
        }
    };


    const handleValidateCode = async () => {
        setError(null);
        setLoading(true);
        try {
            const isValid = await userService.validateRecoveryCode(email, code);
            if (isValid) {
                setStep(3);
            } else {
                setError("Código inválido");
            }
        } catch (err: any) {
            setError(err.response?.data?.message ?? "Error al validar el código");
        } finally {
            setLoading(false);
        }
    };

    const handleChangePassword = async () => {
        if (newPassword !== confirmPassword) {
            setError("Las contraseñas no coinciden");
            return;
        }

        setError(null);
        setLoading(true);
        try {
            const msg = await userService.changePassword(email, code, newPassword);
            alert(msg);
            navigate('/login');
        } catch (err: any) {
            setError(err.response?.data?.message ?? "Error al cambiar la contraseña");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="grid grid-cols-1 md:grid-cols-2 h-screen">
            <div className="bg-[#80AFAB] flex items-center justify-center p-8 border-r-2 border-gray-300">
                <Link className="hover:scale-105 transition-transform" to="/home">
                    <img src="/assets/logo.png" alt="Logo Get-A-Book" className="w-50 mb-6"/>
                    <h1 className="text-4xl font-extrabold text-gray-900">GET-A-BOOK</h1>
                </Link>
            </div>

            <div className="flex items-center justify-center bg-[#80AFAB] p-8">
                <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md border">
                    <h2 className="text-2xl font-bold mb-6 text-center text-gray-900">
                        Recuperar Contraseña
                    </h2>

                    {message && <div className="text-green-600 mb-4 text-sm font-medium">{message}</div>}
                    {error && <div className="text-red-600 mb-4 text-sm font-medium">{error}</div>}

                    {step === 1 && (
                        <>
                            <label className="block mb-2 text-sm font-medium text-gray-700">Correo electrónico</label>
                            <input
                                type="email"
                                placeholder="Ingrese su correo"
                                className="w-full px-4 py-2 mb-4 border rounded-md bg-white text-black hover:scale-105 transition-transform"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                            <div className="space-y-4">
                                <button
                                    onClick={handleSendCode}
                                    className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-4
                                    rounded hover:scale-105 transition-transform border-black border-1"
                                    disabled={loading}
                                >
                                    {loading ? "Enviando..." : "Enviar código"}
                                </button>
                                <button
                                    onClick={() => navigate('/login')}
                                    className="w-full bg-amber-100 hover:bg-amber-200 text-gray-800 font-semibold py-2 px-4
                                    rounded border hover:scale-105 transition-transform"
                                    disabled={loading}
                                >
                                    Volver atrás
                                </button>
                            </div>
                        </>
                    )}

                    {step === 2 && (
                        <>
                            <p className="text-sm text-gray-600 mb-4">Se ha enviado un código a tu correo.</p>
                            <label className="block mb-2 text-sm font-medium text-gray-700">Código de
                                recuperación</label>
                            <input
                                type="text"
                                placeholder="Ingrese el código"
                                className="w-full px-4 py-2 mb-4 border rounded-md bg-amber-100 text-black hover:scale-105 transition-transform"
                                value={code}
                                onChange={(e) => setCode(e.target.value)}
                            />
                            <button
                                onClick={handleValidateCode}
                                className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-4
                                rounded hover:scale-105 transition-transform border-black border-1"
                                disabled={loading}
                            >
                                {loading ? "Validando..." : "Validar código"}
                            </button>
                        </>
                    )}

                    {step === 3 && (
                        <>
                            <label className="block mb-2 text-sm font-medium text-gray-700">Nueva contraseña</label>
                            <input
                                type="password"
                                placeholder="Ingrese nueva contraseña"
                                className="w-full px-4 py-2 mb-4 border rounded-md bg-amber-100 text-black hover:scale-105 transition-transform
                                border-black border-1"
                                value={newPassword}
                                onChange={(e) => setNewPassword(e.target.value)}
                            />

                            <label className="block mb-2 text-sm font-medium text-gray-700">Confirmar contraseña</label>
                            <input
                                type="password"
                                placeholder="Confirme su contraseña"
                                className="w-full px-4 py-2 mb-4 border rounded-md bg-amber-100 text-black hover:scale-105 transition-transform
                                border-black border-1"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                            />

                            <button
                                onClick={handleChangePassword}
                                className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-4
                                rounded hover:scale-105 transition-transform border-black border-1"
                                disabled={loading}
                            >
                                {loading ? "Cambiando..." : "Cambiar contraseña"}
                            </button>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
};

export default RecoverPasswordPage;
