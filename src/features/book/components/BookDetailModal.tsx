import React from "react";
import type { Book } from "../../../types/book";

interface BookDetailModalProps {
  book: Book | null;
  onClose: () => void;
}

const BookDetailModal: React.FC<BookDetailModalProps> = ({ book, onClose }) => {
  if (!book) return null;

  return (
      <div className="fixed inset-0 bg-black/50 backdrop-blur-sm flex items-center justify-center z-[1000]"
           onClick={onClose}>

        <div
            className="bg-white rounded-lg h-[90%] p-6 w-[90%] max-w-7xl relative flex gap-6"
            onClick={(e) => e.stopPropagation()}
        >
          <button
              className="absolute top-3 right-4 text-2xl text-gray-600 hover:text-gray-800"
              onClick={onClose}
          >
            ×
          </button>
          <div className="flex">
            <img
                src={book.imageUrl}
                alt={book.title}
                className="w-44 h-80 rounded object-cover"
            />
            <div className="ml-6 space-y-2">
              <h2 className="text-2xl font-semibold">{book.title}</h2>
              <p><span className="font-semibold">Editorial:</span> {book.publisher}</p>
              <p><span className="font-semibold">Categorías:</span> {book.categories}</p>
              <p><span className="font-semibold">Descripción:</span> {book.description}</p>
              <p><span className="font-semibold">Año:</span> {book.year}</p>
              <p><span className="font-semibold">Valoración:</span> {book.qualification} / 5</p>
              <p><span className="font-semibold">Stock disponible:</span> {book.quantity}</p>
              <p><span className="font-semibold">Precio:</span> ${book.price.toLocaleString()}</p>
              <button className="bg-yellow-400 hover:bg-yellow-500 text-white font-medium py-2 px-4 rounded mt-4">
                Agregar al carrito
              </button>
            </div>
          </div>
        </div>
      </div>
  );
};

export default BookDetailModal;
