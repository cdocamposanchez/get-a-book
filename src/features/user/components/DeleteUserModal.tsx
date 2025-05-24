import React from "react";
import BaseModal from "../../../components/Modal.tsx";

interface DeleteUserModalProps {
    isOpen: boolean;
    onClose: () => void;
    onConfirm: () => void;
    userEmail: string;
}

const DeleteUserModal: React.FC<DeleteUserModalProps> = ({
                                                             isOpen,
                                                             onClose,
                                                             onConfirm,
                                                             userEmail,
                                                         }) => {
    return (
        <BaseModal isOpen={isOpen} onClose={onClose}>
            <div className="p-4 w-full">
                <h2 className="text-lg font-bold text-gray-800 mb-4">Eliminar cuenta</h2>
                <p className="text-sm text-gray-600 mb-6">
                    ¿Estás seguro de que deseas eliminar la cuenta de <strong>{userEmail}</strong>? Esta acción no se puede deshacer.
                </p>
                <div className="flex justify-end gap-2">
                    <button
                        className="px-4 py-2 bg-gray-200 text-gray-700 rounded hover:bg-gray-300 hover:scale-105 transition-transform border-black border-1"
                        onClick={onClose}
                    >
                        Cancelar
                    </button>
                    <button
                        className="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700 hover:scale-105 transition-transform border-black border-1"
                        onClick={onConfirm}
                    >
                        Eliminar
                    </button>
                </div>
            </div>
        </BaseModal>
    );
};

export default DeleteUserModal;
