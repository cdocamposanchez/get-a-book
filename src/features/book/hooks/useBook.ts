import { useState, useEffect, useRef } from "react";
import { bookService } from "../BookService.ts";
import type { Book } from "../../../types/book";
import type { BookFilter } from "../../../types/bookFilter";
import { useDeepCompareEffect } from "react-use";

export const useBooks = (
    filters: BookFilter = {},
    initialPage = 0,
    pageSize = 12,
    debounceMs = 0
) => {
    const [books, setBooks] = useState<Book[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [page, setPage] = useState<number>(initialPage);
    const [totalPages, setTotalPages] = useState<number>(0);

    const prevFiltersRef = useRef<BookFilter>({});

    useDeepCompareEffect(() => {
        if (JSON.stringify(prevFiltersRef.current) !== JSON.stringify(filters)) {
            setPage(0);
            prevFiltersRef.current = filters;
        }
    }, [filters]);


    useEffect(() => {
        const handler = setTimeout(() => {
            const fetchBooks = async () => {
                setLoading(true);
                setError(null);
                try {
                    const response = await bookService.getBooks({
                        ...filters,
                        page,
                        size: pageSize,
                    });

                    setBooks(response.content);
                    setTotalPages(response.totalPages);
                } catch (err: unknown) {
                    setError("Error al cargar los libros: " + (err as Error).message || String(err));
                } finally {
                    setLoading(false);
                }
            };

            fetchBooks();
        }, debounceMs);

        return () => clearTimeout(handler);
    }, [filters, page, pageSize, debounceMs]);

    return {
        books,
        loading,
        error,
        page,
        totalPages,
        setPage,
    };
};
