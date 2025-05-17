import React, { useState } from 'react';
import './HomePage.css';
import books from '..//book.js';
import BookDetailModal from '../components/BookDetailModal';

const HomePage = () => {
  const [selectedBook, setSelectedBook] = useState(null);

  const handleOpenModal = (book: any) => {
    setSelectedBook(book);
  };

  const handleCloseModal = () => {
    setSelectedBook(null);
  };

  return (
    <div className="home-container">
      <aside className="filters">
        <h2>Filtros</h2>
        <h3>Categoría</h3>
        <ul>
          <li>Ficción</li>
          <li>Drama</li>
          <li>Romance</li>
          <li>Terror</li>
          <li>Comedia</li>
        </ul>

        <h3>Ordenar</h3>
        <ul>
          <li>Ascendente</li>
          <li>Descendente</li>
        </ul>

        <h3>Precio</h3>
        <input type="range" min="31000" max="400000" />
        <p>$31.000 - $400.000</p>
      </aside>

      <main className="books-area">
        <div className="books-grid">
          {books.slice(0, 8).map((book) => (
            <div className="book-card" key={book.id} onClick={() => handleOpenModal(book)}>
              <img src={book.image} alt={book.title} />
              <h4>{book.title}</h4>
              <p>{book.author}</p>
              <span>${book.price.toLocaleString()}</span>
            </div>
          ))}
        </div>

        {selectedBook && (
          <BookDetailModal book={selectedBook} onClose={handleCloseModal} />
        )}
      </main>
    </div>
  );
};

export default HomePage;