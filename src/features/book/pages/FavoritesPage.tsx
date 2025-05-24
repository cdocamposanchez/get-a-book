import React, { useEffect, useState } from 'react';
import type { Book } from '../../../types/book';
import { bookService } from '../BookService.ts';
import BookFavoriteCard from '../components/BookFavoriteCard.tsx';
import Spinner from "../../../components/Spinner.tsx";
import {userService} from "../../user/UserService.ts";

const FavoritePage: React.FC = () => {
    const [books, setBooks] = useState<Book[]>([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {

        const fetchFavorites = async () => {
            try {
                setLoading(true);
                const data = await bookService.getFavoriteBooks();
                setBooks(data);
            } catch (error) {
                console.error('FavoritesPage: Error al cargar favoritos', error);
            } finally {
                setLoading(false);
            }
        };
        fetchFavorites();
    }, []);

    const handleRemove = async (bookId: string) => {
        setLoading(true);
        try {
            await userService.updateFavorite(bookId);
            setBooks(prev => prev.filter(book => book.id !== bookId));
        } catch (error) {
            console.error('Error al quitar favorito:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="relative max-h-[90vh]  bg-[#80AFAB] py-10 px-4
         overflow-y-autooverflow-y-auto transition-all duration-120 ease-in-out">
            <h1 className="text-3xl font-bold text-white text-center mb-10">Libros favoritos</h1>
            <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 xl:grid-cols-6 2xl:grid-cols-7 gap-6 justify-items-center">
                {books.map((book) => (
                    <BookFavoriteCard key={book.id} book={book} onRemove={handleRemove} />
                ))}
            </div>
            {loading && (
                <div >
                    <Spinner />
                </div>
            )}
        </div>
    );
};

export default FavoritePage;
