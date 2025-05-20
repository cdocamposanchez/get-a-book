import { useEffect, useState } from "react";
import { getAllBooks } from "../BookService.ts";
import type { Book } from "../../../types/book";

interface Filters {
    category?: string | null;
    sortOrder?: "asc" | "desc" | null;
    maxPrice?: number;
}

export const useBooks = (
    filters: Filters = {},
    initialPage = 0,
    pageSize = 12
) => {
    const [books, setBooks] = useState<Book[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [page, setPage] = useState<number>(initialPage);

    useEffect(() => {
        const fetchBooks = async () => {
            setLoading(true);
            setError(null);
            try {
                let data = await getAllBooks(page, pageSize);

                if (filters.category) {
                    data = data.filter((book) => book.categories === filters.category);
                }

                if (filters.category) {
                    data = data.filter((book) => book.categories === filters.category);
                }

                if (filters.sortOrder === "asc") {
                    data = data.sort((a, b) => a.price - b.price);
                } else if (filters.sortOrder === "desc") {
                    data = data.sort((a, b) => b.price - a.price);
                }

                setBooks(data);
            } catch (err: unknown) {
                setError("Error al cargar los libros: " + err);
            } finally {
                setLoading(false);
            }
        };

        fetchBooks();
    }, [page, pageSize, filters.category, filters.sortOrder, filters.maxPrice]);

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
