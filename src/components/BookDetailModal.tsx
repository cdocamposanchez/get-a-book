import React from "react";
import "./BookDetailModal.css"; // Asegúrate de que este archivo exista
import books from "../book"; // Ruta relativa al archivo con los libros

interface Book {
  id: number;
  title: string;
  author: string;
  genre: string;
  description: string;
  rating: number;
  stock: number;
  image: string;
}

interface BookDetailModalProps {
  book: Book | null;
  onClose: () => void;
}

const BookDetailModal: React.FC<BookDetailModalProps> = ({ book, onClose }) => {
  if (!book) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <button className="close-button" onClick={onClose}>×</button>
        <div className="modal-body">
          <img src={book.image} alt={book.title} className="modal-image" />
          <div className="modal-info">
            <h2>{book.title}</h2>
            <p><strong>Autor:</strong> {book.author}</p>
            <p><strong>Género:</strong> {book.genre}</p>
            <p><strong>Descripción:</strong> {book.description}</p>
            <p><strong>Valoración:</strong> {book.rating} / 5</p>
            <p><strong>Stock disponible:</strong> {book.stock}</p>
            <button className="add-to-cart-button">Agregar al carrito</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookDetailModal;