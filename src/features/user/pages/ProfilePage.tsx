import { useEffect, useState } from "react";
import { userService } from "../UserService.ts";
import type { User } from "../../../types/user";
import { useAuth } from "../hooks/useAuth.ts";
import UserForm from "../components/UserForm.tsx";
import { useNavigate } from "react-router-dom";
import ChangePasswordModal from "../components/ChangePasswordModal.tsx";
import DeleteUserModal from "../components/DeleteUserModal.tsx";
import ChangeEmailModal from "../components/ChangeEmailModal.tsx";

const ProfilePage = () => {
    const { userId } = useAuth();
    const navigate = useNavigate();

    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);
    const [formError, setFormError] = useState<string | null>(null);

    const [showPasswordModal, setShowPasswordModal] = useState(false);
    const [email, setEmail] = useState('');
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [showEmailModal, setShowEmailModal] = useState(false);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                if (!userId) {
                    setFormError("Usuario no autenticado.");
                    return;
                }
                const fetchedUser = await userService.getUserById(userId);
                setUser(fetchedUser);
                setEmail(fetchedUser.email);
            } catch (error) {
                console.error("Error al obtener el usuario:", error);
                setFormError("No se pudo cargar el perfil.");
            } finally {
                setLoading(false);
            }
        };
        fetchUser();
    }, [userId]);

    const handleUpdate = async (updatedFields: Pick<User, "firstName" | "lastName">) => {
        if (!user) return;

        try {
            const updatedUser = await userService.updateUser({ ...user, ...updatedFields });
            setUser(updatedUser);
            setFormError(null);
        } catch (error) {
            console.error("Error al actualizar el usuario:", error);
            setFormError("No se pudo actualizar el perfil.");
        }
    };

    const handleDelete = async () => {
        if (!userId) return;
        try {
            await userService.deleteUser(userId);
            localStorage.removeItem("auth");
            navigate("/login");
        } catch (err) {
            alert("No se pudo eliminar la cuenta. " + err);
        }
    };

    const renderMainContent = () => {
        if (loading) {
            return <p className="text-center text-sm text-gray-600">Cargando perfil...</p>;
        }

        if (user) {
            return <UserForm user={user} onSubmit={handleUpdate} formError={formError} />;
        }

        return <p className="text-center text-red-600">{formError}</p>;
    };

    return (
        <div className="min-h-screen bg-[#80AFAB] font-sans p-2 flex justify-center items-start">
            <div
                className="flex gap-6"
                style={{ maxWidth: "80vw", paddingTop: "2rem", height: "90vh", width: "100%" }}
            >
                <main className="border w-full flex flex-col bg-gray-100 rounded-xl shadow-md p-6 max-h-[52vh] overflow-y-auto">
                    {renderMainContent()}
                </main>

                <aside
                    className="w-1/5 bg-white rounded-lg sticky top-8 h-fit min-w-[250px] max-h-[45vh]
                    overflow-y-auto transition-all duration-120 ease-in-out border p-5 flex flex-col gap-4"
                >
                    <button
                        onClick={() => setShowEmailModal(true)}
                        className="w-full px-4 py-2 bg-white text-black border border-black rounded hover:bg-gray-100 text-sm hover:scale-105 transition-transform"
                    >
                        Editar Correo
                    </button>
                    <button
                        onClick={() => setShowPasswordModal(true)}
                        className="w-full px-4 py-2 bg-white text-black border border-black rounded hover:bg-gray-100 text-sm hover:scale-105 transition-transform"
                    >
                        Cambiar Contraseña
                    </button>
                    <button
                        onClick={() => setShowDeleteModal(true)}
                        className="w-full px-4 py-2 bg-white text-black border border-black rounded hover:bg-gray-100 text-sm hover:scale-105 transition-transform"
                    >
                        Eliminar Cuenta
                    </button>
                </aside>
            </div>
            <ChangePasswordModal
                isOpen={showPasswordModal}
                onClose={() => setShowPasswordModal(false)}
                email={email}
            />

            <ChangeEmailModal
                isOpen={showEmailModal}
                onClose={() => setShowEmailModal(false)}
                oldEmail={email}
            />


            <DeleteUserModal
                isOpen={showDeleteModal}
                onClose={() => setShowDeleteModal(false)}
                onConfirm={handleDelete}
                userEmail={`${user?.email ?? ""}`}
            />

        </div>
    );
};

export default ProfilePage;
