import axiosInstance from '../../utils/axiosConfig.ts';
import type { Book } from '../../types/book';
import type { Response } from '../../types/response';

const BOOK_BASE = '/books';

export const createBook = async (book: Book): Promise<Book> => {
    const formData = new FormData();

    Object.entries(book).forEach(([key, value]) => {
        if (value !== null && value !== undefined) {
            const val = typeof value === 'object' && value instanceof File
                ? value
                : String(value);
            formData.append(key, val);
        }
    });

    const response = await axiosInstance.post<Response<Book>>('/books', formData);
    return response.data.data;
};

export const updateBook = async (book: Book): Promise<Book> => {
    const response = await axiosInstance.put<Response<Book>>(`${BOOK_BASE}`, book);
    return response.data.data;
};

export const deleteBook = async (bookId: string): Promise<string> => {
    const response = await axiosInstance.delete<Response<null>>(`${BOOK_BASE}`, {
        params: { bookId },
    });
    return response.data.message;
};

export const getAllBooks = async (page = 0, size = 10): Promise<Book[]> => {
    const response = await axiosInstance.get<Response<Book[]>>(`${BOOK_BASE}/list`, {
        params: { page, size },
    });
    return response.data.data;
};

export const getBooksByCategory = async (
    category: string,
    page = 0,
    size = 10
): Promise<Book[]> => {
    const response = await axiosInstance.get<Response<Book[]>>(`${BOOK_BASE}/category`, {
        params: { category, page, size },
    });
    return response.data.data;
};

export const getBooksByPublisher = async (
    publisher: string,
    page = 0,
    size = 10
): Promise<Book[]> => {
    const response = await axiosInstance.get<Response<Book[]>>(`${BOOK_BASE}/publisher`, {
        params: { publisher, page, size },
    });
    return response.data.data;
};

export const getBooksByYear = async (
    year: number,
    page = 0,
    size = 10
): Promise<Book[]> => {
    const response = await axiosInstance.get<Response<Book[]>>(`${BOOK_BASE}/year`, {
        params: { year, page, size },
    });
    return response.data.data;
};

export const searchBooksByRegex = async (
    titleRegex: string,
    page = 0,
    size = 10
): Promise<Book[]> => {
    const response = await axiosInstance.get<Response<Book[]>>(`${BOOK_BASE}/search`, {
        params: { titleRegex, page, size },
    });
    return response.data.data;
};

export const getBookById = async (id: string): Promise<Book> => {
    const response = await axiosInstance.get<Response<Book>>(`${BOOK_BASE}/${id}`);
    return response.data.data;
};

export const getFavoriteBooks = async (): Promise<Book[]> => {
    const response = await axiosInstance.get<Response<Book[]>>(`${BOOK_BASE}/favorites`);
    return response.data.data;
};
