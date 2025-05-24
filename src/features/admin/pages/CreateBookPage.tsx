import { useState } from "react";
import { useNavigate } from "react-router-dom";
import BookForm from "../components/BookForm";
import { bookService } from "../../book/BookService";
import type { Book } from "../../../types/book";

const CreateBookPage = () => {
    const [formError, setFormError] = useState<string | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const navigate = useNavigate();

    const handleCreate = async (bookData: Omit<Book, "id" | "imageUrl">) => {
        try {
            setIsSubmitting(true);
            await bookService.createBook(bookData as Book);
            setFormError(null);
            navigate("/admin");
        } catch (error: any) {
            const errorMessage = error.response?.data?.message?.message;
            if (errorMessage) {
                if (errorMessage.includes("A book with this title already exists") || errorMessage.includes("title")) {
                    setFormError("Título de Libro ya Existe");
                } else if (errorMessage.includes("Maximum upload size exceeded")) {
                    setFormError("Tamaño máximo de imagen excedido (max 2MB)");
                } else {
                    setFormError(error.response.data.message);
                }
            } else {
                setFormError("Error al crear libro");
            }
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="p-6 max-w-[70vh] mx-auto m-5 max-h-[80vh] overflow-y-auto bg-white rounded border flex flex-col  items-center">
            <h1 className="text-2xl font-bold mb-4">Crear Nuevo Libro</h1>
            <BookForm onSubmit={handleCreate} formError={formError} />
            {isSubmitting && (
                <p className="text-sm text-gray-500 mt-2">Guardando libro...</p>
            )}
        </div>
    );
};

export default CreateBookPage;
