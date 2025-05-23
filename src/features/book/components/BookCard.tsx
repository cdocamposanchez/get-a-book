import { useState, useEffect } from "react";
import { FaHeart, FaRegHeart } from "react-icons/fa";
import BookDetailModal from "./BookDetailModal.tsx";
import type { Book } from "../../../types/book";
import { userService } from "../../user/UserService.ts";
import { bookService } from "../BookService.ts";

interface BookCardProps {
    books: Book[];
}

const BookCard = ({ books }: BookCardProps) => {
    const [selectedBook, setSelectedBook] = useState<Book | null>(null);
    const [favoriteBooksIds, setFavoriteBooksIds] = useState<string[]>([]);
    const [favoriteLoading, setFavoriteLoading] = useState<string | null>(null);
    const [favoritesLoaded, setFavoritesLoaded] = useState(false);

    const userId = JSON.parse(localStorage.getItem("auth") as string)?.userId;

    useEffect(() => {
        if (!userId) return;

        const cachedFavs = localStorage.getItem(`favoriteBooks_${userId}`);
        if (cachedFavs) {
            setFavoriteBooksIds(JSON.parse(cachedFavs));
            setFavoritesLoaded(true);
        }

        bookService.getFavoriteBooks(userId).then(favs => {
            setFavoriteBooksIds(favs.map(b => b.id));
            setFavoritesLoaded(true);
            localStorage.setItem(`favoriteBooks_${userId}`, JSON.stringify(favs.map(b => b.id)));
        }).catch(console.error);
    }, [userId]);

    const handleOpenModal = (book: Book) => setSelectedBook(book);
    const handleCloseModal = () => setSelectedBook(null);

    const handleFavoriteClick = async (bookId: string) => {
        setFavoriteLoading(bookId);
        try {
            await userService.updateFavorite(bookId);
            setFavoriteBooksIds((prev) =>
                prev.includes(bookId)
                    ? prev.filter((id) => id !== bookId)
                    : [...prev, bookId]
            );
        } catch (error) {
            console.error("Error actualizando favorito", error);
        } finally {
            setFavoriteLoading(null);
        }
    };


    if (!favoritesLoaded) {
        console.log("Loading Favorites...");
    }

    return (
        <div className="max-h-[65vh] w-full p-2 overflow-y-auto transition-all duration-120 ease-in-out">
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 justify-items-center">
                {books.map((book) => {
                    const isFavorite = favoriteBooksIds.includes(book.id);

                    const renderHeartIcon = () => {
                        if (favoriteLoading === book.id) {
                            return <FaHeart className="" />;
                        }
                        if (isFavorite) {
                            return <FaHeart className="" />;
                        }
                        return <FaRegHeart className="" />;
                    };


                    return (
                        <div
                            key={book.id}
                            className="relative border border-opacity-30 bg-white rounded-md p-3 shadow-md hover:scale-[1.02] transform transition-transform w-45 h-70 flex flex-col"
                        >
                            {/* Corazón favorito */}
                            <button
                                onClick={() => handleFavoriteClick(book.id)}
                                className="absolute top-2 right-2 text-red-500 hover:scale-110 hover:text-gray-700 transform transition-transform"
                                title="Agregar o quitar de favoritos"
                            >
                                {renderHeartIcon()}
                            </button>

                            {/* Tarjeta principal clickeable */}
                            <button
                                onClick={() => handleOpenModal(book)}
                                className="text-left w-full h-full flex flex-col"
                            >
                                <img
                                    src={book.imageUrl}
                                    alt={book.title}
                                    className="w-30 h-40 object-cover rounded-md mb-3 self-center flex-shrink-0"
                                />
                                <div className="flex-grow">
                                    <p
                                        className="text-sm font-semibold mb-1 overflow-hidden text-ellipsis line-clamp-2"
                                        style={{
                                            display: '-webkit-box',
                                            WebkitLineClamp: 2,
                                            WebkitBoxOrient: 'vertical',
                                        }}
                                    >
                                        {book.title}
                                    </p>
                                    <p className="text-xs text-gray-700 mb-2 overflow-hidden text-ellipsis whitespace-nowrap">
                                        {book.publisher}
                                    </p>
                                </div>
                                <span className="block font-bold text-sm mt-auto">${book.price.toLocaleString()}</span>
                            </button>
                        </div>
                    );
                })}
            </div>

            {selectedBook && (
                <BookDetailModal book={selectedBook} onClose={handleCloseModal} />
            )}
        </div>
    );
};

export default BookCard;
