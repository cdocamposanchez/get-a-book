import { useState } from "react";
import { bookService } from "../BookService.ts";
import type { Book } from "../../../types/book";
import type { BookFilter } from "../../../types/bookFilter";
import {useDeepCompareEffect} from "react-use";

export const useBooks = (
    filters: BookFilter = {},
    initialPage = 0,
    pageSize = 20
) => {
    const [books, setBooks] = useState<Book[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [page, setPage] = useState<number>(initialPage);

    useDeepCompareEffect(() => {
        const fetchBooks = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await bookService.getBooks({
                    ...filters,
                    page,
                    size: pageSize
                });

                setBooks(response);
            } catch (err: unknown) {
                setError("Error al cargar los libros: " + err);
            } finally {
                setLoading(false);
            }
        };

        fetchBooks();
    }, [filters, page, pageSize]);

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
