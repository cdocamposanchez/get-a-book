import React from "react";
import BookForm from "./BookForm";
import type { Book } from "../../../types/book";
import BaseModal from "../../../components/Modal.tsx";

interface EditBookModalProps {
    isOpen: boolean;
    book: Book | null;
    onClose: () => void;
    onSave: (bookData: Omit<Book, "id" | "imageUrl">) => void;
    formError?: string | null;
}

const EditBookModal: React.FC<EditBookModalProps> = ({ isOpen, book, onClose, onSave, formError }) => {
    if (!book) return null;

    const handleSubmit = (updatedBookData: Omit<Book, "id" | "imageUrl">) => {
        onSave(updatedBookData);
    };

    return (
        <BaseModal isOpen={isOpen} onClose={onClose} className="bg-white max-h-[95vh] overflow-y-auto">
            <h2 className="text-xl font-bold mb-4">Editar Libro</h2>
            <BookForm book={book} onSubmit={handleSubmit} onCancel={onClose} formError={formError} />
        </BaseModal>
    );
};


export default EditBookModal;
