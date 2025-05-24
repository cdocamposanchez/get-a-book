import React, { useState, useEffect } from "react";
import BaseModal from "../../../components/Modal.tsx";
import { userService } from "../UserService.ts";

interface ChangePasswordModalProps {
    isOpen: boolean;
    email: string;
    onClose: () => void;
}

const ChangePasswordModal: React.FC<ChangePasswordModalProps> = ({ isOpen, email: initialEmail, onClose }) => {
    const [step, setStep] = useState<1 | 2 | 3>(1);
    const [method, setMethod] = useState<"default" | "custom">("default");
    const [email, setEmail] = useState(initialEmail || "");
    const [code, setCode] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [modalError, setModalError] = useState<string | null>(null);
    const [message, setMessage] = useState<string | null>(null);
    const [modalLoading, setModalLoading] = useState(false);

    useEffect(() => {
        if (isOpen) {
            setStep(1);
            setMethod("default");
            setEmail(initialEmail);
            setCode("");
            setNewPassword("");
            setConfirmPassword("");
            setModalError(null);
            setMessage(null);
        }
    }, [isOpen, initialEmail]);

    const handleSendCode = async () => {
        setModalError(null);
        setModalLoading(true);
        try {
            const msg = await userService.sendRecoveryCode(email);
            setMessage(msg);
            setStep(2);
        } catch (err: any) {
            const errorMsg = err.response?.data?.message ?? "Error al enviar el código";
            setModalError(errorMsg);
        } finally {
            setModalLoading(false);
        }
    };

    const handleValidateCode = async () => {
        setModalError(null);
        setModalLoading(true);
        try {
            const isValid = await userService.validateRecoveryCode(email, code);
            if (isValid) setStep(3);
            else setModalError("Código inválido");
        } catch (err: any) {
            setModalError(err.response?.data?.message ?? "Error al validar el código");
        } finally {
            setModalLoading(false);
        }
    };

    const handleChangePassword = async () => {
        if (newPassword !== confirmPassword) {
            setModalError("Las contraseñas no coinciden");
            return;
        }

        setModalError(null);
        setModalLoading(true);
        try {
            const msg = await userService.changePassword(email, code, newPassword);
            alert(msg);
            onClose();
        } catch (err: any) {
            setModalError(err.response?.data?.message ?? "Error al cambiar la contraseña");
        } finally {
            setModalLoading(false);
        }
    };

    return (
        <BaseModal isOpen={isOpen} onClose={onClose}>
            <h2 className="text-xl font-bold mb-4">Autenticación para cambio de contraseña</h2>
            {message && <p className="text-green-600 text-sm mb-2">{message}</p>}
            {modalError && <p className="text-red-600 text-sm mb-2">{modalError}</p>}

            {step === 1 && (
                <>
                    <div className="mb-4">
                        <p className="text-sm mb-2">Selecciona un método para recibir el código:</p>
                        <label className="block text-sm mb-1">
                            <input
                                type="radio"
                                className="mr-2"
                                value="default"
                                checked={method === "default"}
                                onChange={() => {
                                    setMethod("default");
                                    setEmail(initialEmail);
                                }}
                            />
                            Usar correo registrado ({initialEmail})
                        </label>
                        <label className="block text-sm">
                            <input
                                type="radio"
                                className="mr-2"
                                value="custom"
                                checked={method === "custom"}
                                onChange={() => setMethod("custom")}
                            />
                            Usar otro correo electrónico
                        </label>
                    </div>

                    {method === "custom" && (
                        <>
                            <p className="text-gray-500 italic mb-4">Próximamente</p>
                        </>
                    )}

                    <button
                        onClick={handleSendCode}
                        disabled={method === "custom"}
                        className={`w-full text-white rounded py-2 border-black border-1
                        ${method === "custom"
                            ? "bg-gray-400 cursor-not-allowed"
                            : "bg-blue-600 hover:bg-blue-700 hover:scale-105 transition-transform"
                        }`}
                    >
                        {modalLoading ? "Enviando..." : "Enviar código"}
                    </button>
                </>
            )}

            {step === 2 && (
                <>
                    <label className="block text-sm">Código recibido</label>
                    <input
                        type="text"
                        className="border p-2 rounded w-full mb-4"
                        value={code}
                        onChange={(e) => setCode(e.target.value)}
                    />
                    <button
                        onClick={handleValidateCode}
                        className="w-full bg-green-600 text-white rounded py-2 hover:bg-green-700 hover:scale-105
                        transition-transform border-black border-1"
                    >
                        {modalLoading ? "Validando..." : "Validar código"}
                    </button>
                </>
            )}

            {step === 3 && (
                <>
                    <label className="block text-sm">Nueva contraseña</label>
                    <input
                        type="password"
                        className="border p-2 rounded w-full mb-2"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                    />
                    <label className="block text-sm">Confirmar contraseña</label>
                    <input
                        type="password"
                        className="border p-2 rounded w-full mb-4"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                    <button
                        onClick={handleChangePassword}
                        className="w-full bg-indigo-600 text-white rounded py-2 hover:bg-indigo-700 hover:scale-105
                        transition-transform border-black border-1"
                    >
                        {modalLoading ? "Cambiando..." : "Cambiar contraseña"}
                    </button>
                </>
            )}
        </BaseModal>
    );
};

export default ChangePasswordModal;
