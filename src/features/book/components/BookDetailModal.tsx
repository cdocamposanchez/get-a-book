// components/BookDetailModal.tsx
import React, { useState } from "react";
import type { Book } from "../../../types/book";
import type { CartItem } from "../../../types/cartItem.d.ts";
import StarRating from "./StarRating.tsx";
import BaseModal from "../../../components/Modal.tsx";

interface BookDetailModalProps {
  book: Book | null;
  onClose: () => void;
}

const addToCart = (book: Book, quantity: number) => {
  const currentCart = JSON.parse(localStorage.getItem("cart") ?? "[]") as CartItem[];

  const existingItemIndex = currentCart.findIndex(item => item.id === book.id);

  if (existingItemIndex >= 0) {
    currentCart[existingItemIndex].quantity += quantity;
  } else {
    currentCart.push({
      id: book.id,
      title: book.title,
      price: book.price,
      quantity,
      image: book.imageUrl
    });
  }

  localStorage.setItem("cart", JSON.stringify(currentCart));
};

const BookDetailModal: React.FC<BookDetailModalProps> = ({ book, onClose }) => {
  const [quantity, setQuantity] = useState(1);

  if (!book) return null;

  const increase = () => setQuantity((q) => q + 1);
  const decrease = () => setQuantity((q) => (q > 1 ? q - 1 : 1));

  return (
      <BaseModal isOpen={!!book} onClose={onClose} className="bg-[#80AFAB] text-white w-4/5 h-3/4 flex gap-6">
        {/* Imagen */}
        <div className="flex-shrink-0">
          <img
              src={book.imageUrl}
              alt={book.title}
              className="w-44 h-[280px] object-cover rounded shadow-md"
          />
          <p className="text-center mt-2 text-sm">{book.publisher}</p>
          <p className="text-center text-xs text-gray-300">{book.year}</p>
        </div>

        {/* Info */}
        <div className="flex flex-col space-y-2 flex-1 text-black">
        <span className="text-white bg-gray-300 text-xs px-2 py-1 rounded w-fit font-semibold">
          {book.categories}
        </span>
          <h2 className="text-xl font-bold text-white">{book.title}</h2>
          <p className="text-sm text-white leading-snug">{book.description}</p>

          {/* Valoración */}
          <StarRating rating={book.qualification} />

          {/* Cantidad y botón */}
          <div className="flex items-center mt-auto justify-end space-x-2">
            <div className="flex items-center space-x-2 mr-5">
              <span className="text-white text-sm font-medium">Cantidad</span>
              <button
                  onClick={decrease}
                  className="bg-white text-black px-2 rounded text-xl font-bold hover:bg-green-100 transition"
              >
                -
              </button>
              <span className="w-6 text-center">{quantity}</span>
              <button
                  onClick={increase}
                  className="bg-white text-black px-2 rounded text-xl font-bold hover:bg-green-100 transition"
              >
                +
              </button>
            </div>

            <button
                className="bg-white hover:bg-green-100 transition text-black font-bold py-2 px-4 rounded"
                onClick={() => {
                  addToCart(book, quantity);
                  onClose();
                }}
            >
              AGREGAR AL CARRITO
            </button>
          </div>
        </div>
      </BaseModal>
  );
};

export default BookDetailModal;
