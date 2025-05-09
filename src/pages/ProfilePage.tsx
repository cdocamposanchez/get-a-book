import { useState } from "react";

const ProfilePage = () => {
    const [userInfo] = useState({
        name: "Juan Pérez",
        email: "juan@example.com",
        phone: "+1234567890",
        address: "Calle Ficticia 123, Ciudad",
    });

    return (
        <div className="min-h-screen bg-gray-100 py-8 px-4">
            <div className="max-w-lg mx-auto bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-3xl font-semibold text-center mb-6 text-gray-800">Mi Perfil</h2>
                <div className="space-y-4">
                    <p><strong>Nombre:</strong> {userInfo.name}</p>
                    <p><strong>Correo Electrónico:</strong> {userInfo.email}</p>
                    <p><strong>Teléfono:</strong> {userInfo.phone}</p>
                    <p><strong>Dirección:</strong> {userInfo.address}</p>
                </div>
                <div className="mt-6 text-center">
                    <button className="w-full py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700">
                        Editar Perfil
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ProfilePage;
