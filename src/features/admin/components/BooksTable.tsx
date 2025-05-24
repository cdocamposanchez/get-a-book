import React from "react";
import { FaEdit, FaTrash } from "react-icons/fa";
import type { Book } from "../../../types/book";

interface BooksTableProps {
    books: Book[];
    page: number;
    size: number;
    onEdit: (book: Book) => void;
    onDelete: (book: Book) => void;
}

const BooksTable: React.FC<BooksTableProps> = ({ books, page, size, onEdit, onDelete }) => {
    return (
        <div className="overflow-x-auto rounded max-h-[75vh] overflow-y-auto border shadow">
        <table className="min-w-full text-sm text-left text-gray-700 bg-white">
        <thead className="bg-gray-100 text-xs uppercase sticky top-0 z-10">
        <tr>
            <th className="px-4 py-3">#</th>
            <th className="px-6 py-3">Título</th>
            <th className="px-6 py-3">Autor</th>
            <th className="px-6 py-3">Categoría</th>
            <th className="px-6 py-3">Año</th>
            <th className="px-6 py-3">Precio</th>
            <th className="px-6 py-3">Cantidad</th>
            <th className="px-6 py-3 text-center">Acciones</th>
        </tr>
        </thead>
        <tbody>
        {books.map((book, index) => (
            <tr key={book.id} className="border-b hover:bg-gray-50">
                <td className="px-4 py-3">{page * size + index + 1}</td>
                <td className="px-6 py-3 text-xs">{book.title}</td>
                <td className="px-6 py-3 text-xs">{book.publisher}</td>
                <td className="px-6 py-3 text-xs text-wrap">{book.categories}</td>
                <td className="px-6 py-3">{book.year}</td>
                <td className="px-6 py-3">${book.price.toFixed(2)}</td>
                <td className="px-6 py-3 text-center">{book.quantity}</td>
                <td className="px-6 py-3 flex gap-2 justify-center">
                    <button
                        className="p-1 rounded hover:bg-blue-100 text-blue-600"
                        onClick={() => onEdit(book)}
                        aria-label="Editar libro"
                        >
                        <FaEdit size={16} />
                    </button>
                    <button
                        className="p-1 rounded hover:bg-red-100 text-red-600"
                        onClick={() => onDelete(book)}
                        aria-label="Eliminar libro"
                        >
                        <FaTrash size={16} />
                    </button>
                </td>
            </tr>
))}
    </tbody>
    </table>
    </div>
);
};

export default BooksTable;
