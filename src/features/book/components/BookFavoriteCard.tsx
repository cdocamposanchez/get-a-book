import React, { useState } from 'react';
import type { Book } from '../../../types/book';
import { FaHeart, FaHeartBroken } from 'react-icons/fa';

interface BookFavoriteCardProps {
    book: Book;
    onRemove: (bookId: string) => void;
}

const BookFavoriteCard: React.FC<BookFavoriteCardProps> = ({ book, onRemove }) => {
    const [hovered, setHovered] = useState(false);

    const handleClick = () => {
        onRemove(book.id);
    };

    return (
        <div
            className="flex flex-col items-center w-40 transition-transform transform hover:scale-105"
            onMouseEnter={() => setHovered(true)}
            onMouseLeave={() => setHovered(false)}
        >
            <div className="relative">
                <img
                    src={book.imageUrl}
                    alt={book.title}
                    className="h-60 w-40 object-cover rounded-md shadow-md"
                />
                <button
                    title="Quitar de favoritos"
                    onClick={handleClick}
                    className="absolute bottom-2 left-2 bg-white rounded-full p-1 shadow transition-transform hover:scale-110"
                >
                    {hovered ? (
                        <FaHeartBroken className="text-red-600 transition-all duration-200" />
                    ) : (
                        <FaHeart className="text-red-500 transition-all duration-200" />
                    )}
                </button>
            </div>
            <div className="text-center mt-2">
                <p className="text-sm font-semibold line-clamp-2">{book.title}</p>
                <p className="text-xs text-gray-700">{book.publisher}</p>
                <p className="text-xs font-semibold">${book.price.toLocaleString()}</p>
            </div>
        </div>
    );
};

export default BookFavoriteCard;
