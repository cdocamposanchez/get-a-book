import { useState } from "react";
import BookDetailModal from "./BookDetailModal.tsx";
import type { Book } from "../../../types/book";

interface BookCardProps {
    books: Book[];
}

const BookCard = ({ books }: BookCardProps) => {
    const [selectedBook, setSelectedBook] = useState<Book | null>(null);

    const handleOpenModal = (book: Book) => {
        setSelectedBook(book);
    };

    const handleCloseModal = () => {
        setSelectedBook(null);
    };

    return (
        <main className="flex-1 overflow-y-auto">
            <div className="grid grid-cols-4 gap-6 justify-items-center">
                {books.map((book) => (
                    <div
                        key={book.id}
                        onClick={() => handleOpenModal(book)}
                        className="cursor-pointer bg-gray-100 rounded-md p-3 shadow-md hover:scale-105 transform transition-transform"
                    >
                        <img
                            src={book.imageUrl}
                            alt={book.title}
                            className="w-50 h-70 object-cover rounded-md mb-3"
                        />
                        <h4 className="text-sm font-semibold mb-1 text-wrap">{book.title}</h4>
                        <p className="text-xs text-gray-700 mb-1 text-wrap">{book.publisher}</p>
                        <span className="block font-bold text-sm">${book.price.toLocaleString()}</span>
                    </div>
                ))}
            </div>

            {selectedBook && (
                <BookDetailModal book={selectedBook} onClose={handleCloseModal} />
            )}
        </main>
    );
};

export default BookCard;
