import { useState } from "react";
import BookDetailModal from "./BookDetailModal.tsx";
import type { Book } from "../../../types/book";

interface BookCardProps {
    books: Book[];
}
const BookCard = ({ books }: BookCardProps) => {
    const [selectedBook, setSelectedBook] = useState<Book | null>(null);

    const handleOpenModal = (book: Book) => setSelectedBook(book);
    const handleCloseModal = () => setSelectedBook(null);

    return (
        <div className="overflow-y-auto max-h-[65vh] w-full">
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 justify-items-center">
                {books.map((book) => (
                    <button
                        key={book.id}
                        onClick={() => handleOpenModal(book)}
                        className="cursor-pointer bg-white rounded-md p-3 shadow-md hover:scale-105 transform transition-transform text-left w-45 h-70 flex flex-col"
                    >
                        <img
                            src={book.imageUrl}
                            alt={book.title}
                            className="w-30 h-40 object-cover rounded-md mb-3 self-center flex-shrink-0"
                        />
                        <div className="flex-grow">
                            <p
                                className="text-sm font-semibold mb-1 overflow-hidden text-ellipsis line-clamp-2"
                                style={{display: '-webkit-box', WebkitLineClamp: 2, WebkitBoxOrient: 'vertical'}}
                            >
                                {book.title}
                            </p>
                            <p
                                className="text-xs text-gray-700 mb-2 overflow-hidden text-ellipsis whitespace-nowrap"
                            >
                                {book.publisher}
                            </p>
                        </div>
                        <span className="block font-bold text-sm mt-auto">${book.price.toLocaleString()}</span>
                    </button>
                ))}
            </div>

            {selectedBook && (
                <BookDetailModal book={selectedBook} onClose={handleCloseModal}/>
            )}
        </div>
    );
};


export default BookCard;
