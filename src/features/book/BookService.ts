import axiosInstance from '../../utils/axiosConfig.ts';
import type { Book } from '../../types/book';
import type { Response } from '../../types/response';
import type {BookFilter} from "../../types/bookFilter";

const BOOK_BASE = '/books';

export const bookService = {
    createBook: async (book: Book): Promise<Book> => {
        const formData = new FormData();

        Object.entries(book).forEach(([key, value]) => {
            if (value !== null && value !== undefined) {
                const val = typeof value === 'object' && value instanceof File
                    ? value
                    : String(value);
                formData.append(key, val);
            }
        });

        const response = await axiosInstance.post<Response<Book>>(BOOK_BASE, formData);
        return response.data.data;
    },

    updateBook: async (book: Book): Promise<Book> => {
        const response = await axiosInstance.put<Response<Book>>(BOOK_BASE, book);
        return response.data.data;
    },

    deleteBook: async (bookId: string): Promise<string> => {
        const response = await axiosInstance.delete<Response<null>>(BOOK_BASE, {
            params: { bookId },
        });
        return response.data.message;
    },

    getBooks: async (
        filters: BookFilter
    ): Promise<Book[]> => {
        const {
            category,
            publisher,
            year,
            titleRegex,
            minPrice,
            maxPrice,
            page = 0,
            size = 10,
            sortOrder = 'ASC',
        } = filters;

        const params: Record<string, string | number> = { page, size, sortOrder };

        if (category) params.category = category;
        if (publisher) params.publisher = publisher;
        if (year !== undefined) params.year = year;
        if (titleRegex) params.titleRegex = titleRegex;
        if (minPrice !== undefined) params.minPrice = minPrice;
        if (maxPrice !== undefined) params.maxPrice = maxPrice;

        console.log(params);

        const response = await axiosInstance.get<Response<Book[]>>(BOOK_BASE, {
            params,
        });
        return response.data.data;
    },

    getBookById: async (id: string): Promise<Book> => {
        const response = await axiosInstance.get<Response<Book>>(`${BOOK_BASE}/${id}`);
        return response.data.data;
    },


    getFavoriteBooks: async (userId: string, page = 0, size = 10): Promise<Book[]> => {
        const response = await axiosInstance.get<Response<Book[]>>(`${BOOK_BASE}/favorites`, {
            params: { userId, page, size }
        });
        return response.data.data;
    }
};
