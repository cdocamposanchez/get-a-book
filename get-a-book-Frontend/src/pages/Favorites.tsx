// src/pages/Favorites.tsx
import React from "react";
import "./Favorites.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeart, faTrashAlt } from "@fortawesome/free-solid-svg-icons";

function Favorites() {
  const favorites = [
    {
      id: "1",
      title: "Cien años de soledad",
      author: "Gabriel García Márquez",
      image: "https://via.placeholder.com/100x140?text=Libro+1",
    },
    {
      id: "2",
      title: "1984",
      author: "George Orwell",
      image: "https://via.placeholder.com/100x140?text=Libro+2",
    },
  ];

  return (
    <div className="favorites-container">
      <h1><FontAwesomeIcon icon={faHeart} /> Favoritos</h1>
      <div className="favorites-list">
        {favorites.map((book) => (
          <div key={book.id} className="favorite-card">
            <img src={book.image} alt={book.title} />
            <div className="book-info">
              <h3>{book.title}</h3>
              <p>{book.author}</p>
            </div>
            <button className="remove-button">
              <FontAwesomeIcon icon={faTrashAlt} /> Quitar
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Favorites;