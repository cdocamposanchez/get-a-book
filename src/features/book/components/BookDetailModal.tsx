// components/BookDetailModal.tsx
import React, { useState } from "react";
import type { Book } from "../../../types/book";
import type { CartItem } from "../../../types/cartItem.d.ts";
import StarRating from "./StarRating.tsx";
import BaseModal from "../../../components/Modal.tsx";
import {UseCart} from "../../order/hooks/UseCart.ts";

interface BookDetailModalProps {
  book: Book | null;
  onClose: () => void;
}

const BookDetailModal: React.FC<BookDetailModalProps> = ({ book, onClose }) => {
  const [quantity, setQuantity] = useState(1);
  const { updateCart } = UseCart();

  if (!book) return null;

  const increase = () => setQuantity((q) => q + 1);
  const decrease = () => setQuantity((q) => (q > 1 ? q - 1 : 1));

  const handleAddToCart = () => {
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
    updateCart();
    onClose();
  };

  return (
      <BaseModal isOpen={!!book} onClose={onClose} className="bg-white text-white w-4/5 h-3/4 flex gap-6 border-black border-1">
        {/* Imagen */}
        <div className="flex-shrink-0">
          <img
              src={book.imageUrl}
              alt={book.title}
              className="w-60 h-90 object-cover rounded shadow-md border-black border-1"
          />
          <p className="text-center mt-2 text-sm text-black font-semibold">{book.publisher}</p>
          <p className="text-center text-xs text-black">{book.year}</p>
        </div>

        {/* Info */}
        <div className="flex flex-col space-y-2 flex-1 text-black">
          <div className="flex flex-wrap gap-2">
            {book.categories.split(',').map((category, index) => (
                <span
                    key={index}
                    className="text-black border bg-gray-200 text-xs px-2 py-1 rounded w-fit font-semibold"
                >
                  {category.trim()}
                </span>
            ))}
          </div>
          <h2 className="text-xl font-bold text-black">{book.title}</h2>
          <p className="text-sm text-black leading-snug">{book.description}</p>

          {/* Valoración */}
          <StarRating rating={book.qualification}/>

          {/* Cantidad y botón */}
          <div className="flex items-center mt-auto justify-end space-x-2">
            <div className="flex items-center space-x-2 mr-5">
              <button
                  onClick={decrease}
                  className="bg-white text-black px-2 rounded text-xl font-bold hover:bg-gray-200 hover:scale-105 transition-transform border"
              >
                -
              </button>
              <span className="w-6 text-center">{quantity}</span>
              <button
                  onClick={increase}
                  className="bg-white text-black px-2 rounded text-xl font-bold hover:bg-gray-200 border hover:scale-105 transition-transform"
              >
                +
              </button>
            </div>

            <button
                className="bg-green-600 hover:bg-green-700 border text-black font-bold py-2 px-4 rounded hover:scale-105 transition-transform"
                onClick={handleAddToCart}
            >
              AGREGAR AL CARRITO
            </button>
          </div>
        </div>
      </BaseModal>
  );
};

export default BookDetailModal;
