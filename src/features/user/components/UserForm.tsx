import React, { useState, type FormEvent } from "react";
import type { User } from "../../../types/user";

interface UserFormProps {
    user: User;
    onSubmit: (updatedUser: Pick<User, "firstName" | "lastName">) => void;
    formError?: string | null;
}

const UserForm: React.FC<UserFormProps> = ({ user, onSubmit, formError }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [firstName, setFirstName] = useState(user.firstName);
    const [lastName, setLastName] = useState(user.lastName);

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault();
        onSubmit({ firstName, lastName });
        setIsEditing(false);
    };

    const handleCancel = () => {
        setFirstName(user.firstName);
        setLastName(user.lastName);
        setIsEditing(false);
    };

    if (!isEditing) {
        return (
            <div className="space-y-2 max-w-lg min-w-full">
                <div>
                    <label className="block font-medium text-sm">Nombre</label>
                    <p className="text-sm border px-2 py-1 rounded">{user.firstName}</p>
                </div>
                <div>
                    <label className="block font-medium text-sm">Apellido</label>
                    <p className="text-sm border px-2 py-1 rounded">{user.lastName}</p>
                </div>
                <div>
                    <label className="block font-medium text-sm">Correo</label>
                    <p className="text-sm border px-2 py-1 rounded">{user.email}</p>
                </div>
                <div>
                    <label className="block font-medium text-sm">Contraseña</label>
                    <p className="text-sm border px-2 py-1 rounded">********</p>
                </div>
                <button
                    onClick={() => setIsEditing(true)}
                    className="px-3 py-1.5 bg-white text-black border-black border-1 rounded hover:bg-gray-100 text-sm hover:scale-105 transition-transform"
                >
                    Editar Datos Personales
                </button>
            </div>
        );
    }

    return (
        <form onSubmit={handleSubmit} className="space-y-2 max-w-md">
            {formError && (
                <p className="text-red-600 text-sm mt-1">{formError}</p>
            )}
            <div>
                <label className="block font-medium text-sm mb-0.5" htmlFor="firstName">Nombre</label>
                <input
                    id="firstName"
                    type="text"
                    value={firstName}
                    onChange={e => setFirstName(e.target.value)}
                    required
                    className="w-full border rounded px-2 py-1 text-sm hover:scale-105 transition-transform"
                />
            </div>
            <div>
                <label className="block font-medium text-sm mb-0.5" htmlFor="lastNames">Apellidos</label>
                <input
                    id="lastNames"
                    type="text"
                    value={lastName}
                    onChange={e => setLastName(e.target.value)}
                    required
                    className="w-full border rounded px-2 py-1 text-sm hover:scale-105 transition-transform"
                />
            </div>
            <div className="flex gap-2">
                <button
                    type="submit"
                    className="px-3 py-1.5 bg-white text-black border-black border-1 rounded hover:bg-gray-100 text-sm hover:scale-105 transition-transform"
                >
                    Guardar
                </button>
                <button
                    type="button"
                    onClick={handleCancel}
                    className="px-3 py-1.5 bg-white text-black border-black border-1 rounded hover:bg-gray-100 text-sm hover:scale-105 transition-transform"
                >
                    Cancelar
                </button>
            </div>
        </form>
    );
};

export default UserForm;
