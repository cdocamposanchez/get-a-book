import React, { useState, useEffect } from "react";
import BaseModal from "../../../components/Modal.tsx";
import { userService } from "../UserService.ts";
import { useNavigate } from "react-router-dom";

interface ChangeEmailModalProps {
    isOpen: boolean;
    oldEmail: string;
    onClose: () => void;
}

const ChangeEmailModal: React.FC<ChangeEmailModalProps> = ({ isOpen, oldEmail, onClose }) => {
    const [step, setStep] = useState<1 | 2 | 3>(1);
    const [email, setEmail] = useState(oldEmail);
    const [newEmail, setNewEmail] = useState("");
    const [code, setCode] = useState("");
    const [modalError, setModalError] = useState<string | null>(null);
    const [message, setMessage] = useState<string | null>(null);
    const [modalLoading, setModalLoading] = useState(false);

    const navigate = useNavigate();

    useEffect(() => {
        if (isOpen) {
            setStep(1);
            setEmail(oldEmail);
            setNewEmail("");
            setCode("");
            setModalError(null);
            setMessage(null);
        }
    }, [isOpen, oldEmail]);

    const handleSendCode = async () => {
        setModalError(null);
        setModalLoading(true);
        try {
            const msg = await userService.sendRecoveryCode(email);
            setMessage(msg);
            setStep(2);
        } catch (err: any) {
            setModalError(err.response?.data?.message ?? "Error al enviar el código");
        } finally {
            setModalLoading(false);
        }
    };

    const handleValidateCode = async () => {
        setModalError(null);
        setModalLoading(true);
        try {
            const isValid = await userService.validateRecoveryCode(email, code);
            if (isValid) {
                setStep(3);
                setMessage(null);
            } else {
                setModalError("Código inválido");
            }
        } catch (err: any) {
            setModalError(err.response?.data?.message ?? "Error al validar el código");
        } finally {
            setModalLoading(false);
        }
    };

    const handleChangeEmail = async () => {
        setModalError(null);
        setModalLoading(true);
        try {
            const msg = await userService.changeEmail({
                oldEmail: email,
                newEmail,
                recoveryCode: code,
            });
            setMessage(msg);

            setTimeout(() => {
                navigate("/logout");
            }, 2500);
        } catch (err: any) {
            setModalError(err.response?.data?.message ?? "Error al cambiar el correo");
        } finally {
            setModalLoading(false);
        }
    };

    return (
        <BaseModal isOpen={isOpen} onClose={onClose}>
            <h2 className="text-xl font-bold mb-4">Autenticación para cambio de correo</h2>
            {message && <p className="text-green-600 text-sm mb-2">{message}</p>}
            {modalError && <p className="text-red-600 text-sm mb-2">{modalError}</p>}

            {step === 1 && (
                <>
                    <p className="text-sm mb-2">Se enviará un código al correo registrado: <strong>{email}</strong></p>
                    <button
                        onClick={handleSendCode}
                        className="w-full bg-blue-600 text-white rounded py-2 hover:bg-blue-700 hover:scale-105
                        transition-transform border-black border-1"
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
                    {message ? (
                        <p className="text-green-600 text-sm mb-4">
                            Cerrando Sesión...
                        </p>
                    ) : (
                        <>
                            <label className="block text-sm">Nuevo correo electrónico</label>
                            <input
                                type="email"
                                className="border p-2 rounded w-full mb-4"
                                value={newEmail}
                                onChange={(e) => setNewEmail(e.target.value)}
                            />
                            <button
                                onClick={handleChangeEmail}
                                className="w-full bg-indigo-600 text-white rounded py-2 hover:bg-indigo-700
                                 border-black border-1 hover:scale-105 transition-transform"
                            >
                                {modalLoading ? "Cambiando..." : "Cambiar correo"}
                            </button>
                        </>
                    )}
                </>
            )}

        </BaseModal>
    );
};

export default ChangeEmailModal;
