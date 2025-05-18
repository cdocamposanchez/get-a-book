import {useState} from "react";
import books from "../../../book.ts";
import BookDetailModal from "./BookDetailModal.tsx";

const BookCard = () => {
    const [selectedBook, setSelectedBook] = useState(null);

    const handleOpenModal = (book: any) => {
        setSelectedBook(book);
    };

    const handleCloseModal = () => {
        setSelectedBook(null);
    };

    return (
        <main className="books-area">
            <div className="books-grid">
                {books.slice(0, 8).map((book) => (
                    <div className="book-card" key={book.author} onClick={() => handleOpenModal(book)}>
                        <img src={book.image} alt={book.title}/>
                        <h4>{book.title}</h4>
                        <p>{book.author}</p>
                        <span>${book.price.toLocaleString()}</span>
                    </div>
                ))}
            </div>

            {selectedBook && (
                <BookDetailModal book={selectedBook} onClose={handleCloseModal}/>
            )}
        </main>
    );
};

export default BookCard;
