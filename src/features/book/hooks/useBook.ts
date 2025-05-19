import { useEffect, useState } from "react";
import { getAllBooks } from "../BookService.ts";
import type {Book} from "../../../types/book";

export const useBooks = (initialPage = 0, pageSize = 12) => {
    const [books, setBooks] = useState<Book[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [page, setPage] = useState<number>(initialPage);

    useEffect(() => {
        const fetchBooks = async () => {
            setLoading(true);
            setError(null);
            try {
                const data = await getAllBooks(page, pageSize);
                setBooks(data);
            } catch (err: unknown) {
                setError("Error al cargar los libros" + err);
            } finally {
                setLoading(false);
            }
        };

        fetchBooks();
    }, [page, pageSize]);

    const nextPage = () => setPage((prev) => prev + 1);
    const prevPage = () => setPage((prev) => Math.max(prev - 1, 0));

    return {
        books,
        loading,
        error,
        page,
        nextPage,
        prevPage,
        setPage,
    };
};
