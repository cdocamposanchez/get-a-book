import React, { useEffect, useState } from "react";
import ReactPaginate from "react-paginate";
import { bookService } from "../BookService.ts";
import type { Book } from "../../../types/book";
import BooksTable from "../components/BooksTable.tsx";
import EditBookModal from "../components/EditBookModal.tsx";
import DeleteBookModal from "../components/DeleteBookModal.tsx";
import {useSearch} from "../../../context/SearchContext.tsx";

const BooksPage = () => {
    const [books, setBooks] = useState<Book[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [page, setPage] = useState<number>(0);
    const [size, setSize] = useState<number>(10);
    const [totalPages, setTotalPages] = useState<number>(0);
    const [formError, setFormError] = useState<string | null>(null);
    const [selectedBook, setSelectedBook] = useState<Book | null>(null);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
    const [bookToDelete, setBookToDelete] = useState<Book | null>(null);

    const { searchTerm, setSearchTerm } = useSearch();

    const filteredBooks = books.filter((book) =>
        book.title.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const fetchBooks = async () => {
        try {
            setLoading(true);
            const data = await bookService.getBooks({ page, size });
            setBooks(data.content);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error("Error fetching books:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchBooks();
    }, [page, size]);

    const handleSizeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSize(Number(e.target.value));
        setPage(0);
    };

    const handleEdit = (book: Book) => {
        setSelectedBook(book);
        setIsEditModalOpen(true);
    };

    const handleDelete = (book: Book) => {
        setBookToDelete(book);
        setIsDeleteModalOpen(true);
    };

    const handleSave = async (updatedBookData: Omit<Book, "id" | "imageUrl">) => {
        if (!selectedBook) return;

        try {
            const bookToUpdate: Book = {
                id: selectedBook.id,
                imageUrl: selectedBook.imageUrl,
                ...updatedBookData,
            };

            console.log(bookToUpdate);

            await bookService.updateBook(bookToUpdate);

            setIsEditModalOpen(false);
            setSelectedBook(null);
            setFormError(null);
            fetchBooks();
        } catch (error: any) {

            const errorMessage = error.response?.data?.message?.message;
            if (errorMessage) {
                if (errorMessage.includes("A book with this title already exists") || errorMessage.includes("title")) {
                    setFormError("Título de Libro ya Existe");
                } else if (errorMessage.includes("Maximum upload size exceeded")) {
                    setFormError("Tamaño máximo de imagen excedido (max 2MB");
                } else {
                    setFormError(error.response.data.message);
                }
            } else {
                setFormError("Error al actualizar libro");
            }
        }
    };

    const handleConfirmDelete = async () => {
        if (!bookToDelete) return;

        try {
            await bookService.deleteBook(bookToDelete.id);
            setBookToDelete(null);
            setIsDeleteModalOpen(false);
            fetchBooks();
        } catch (error) {
            console.error("Error eliminando libro:", error);
        }
    };

    const handleCloseModal = () => {
        setIsEditModalOpen(false);
        setSelectedBook(null);
    };

    return (
        <div className="p-6">
            <div className="flex justify-between items-center mb-4">
                <h1 className="text-2xl font-bold">Inventario Libros</h1>
                <input
                    type="text"
                    placeholder="Buscar por título..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="border rounded px-3 py-1 text-sm w-64 bg-white"
                />

                <ReactPaginate
                    previousLabel="<"
                    nextLabel=">"
                    breakLabel="..."
                    pageCount={totalPages}
                    marginPagesDisplayed={2}
                    pageRangeDisplayed={2}
                    onPageChange={(e) => setPage(e.selected)}
                    forcePage={page}
                    containerClassName="flex gap-1"
                    pageClassName="w-8 h-8 rounded-md font-medium text-sm text-gray-700 border hover:bg-gray-200 flex
                    items-center justify-center cursor-pointer hover:scale-105 transition-transform"
                    pageLinkClassName="w-full h-full flex items-center justify-center hover:scale-105 transition-transform"
                    activeClassName="bg-white text-gray-700 hover:scale-105 transition-transform"
                    activeLinkClassName="w-full h-full flex items-center justify-center hover:scale-105 transition-transform"
                    previousClassName="w-8 h-8 rounded-md bg-white text-gray-700 font-medium text-sm flex items-center
                    justify-center cursor-pointer border hover:bg-gray-200 hover:scale-105 transition-transform"
                    previousLinkClassName="w-full h-full flex items-center justify-center"
                    nextClassName="w-8 h-8 rounded-md bg-white text-gray-700 font-medium text-sm flex items-center
                    justify-center cursor-pointer border hover:bg-gray-200 hover:scale-105 transition-transform"
                    nextLinkClassName="w-full h-full flex items-center justify-center"
                    disabledClassName="opacity-50 cursor-not-allowed"
                    breakClassName="w-8 text-center text-gray-700 select-none flex items-center justify-center
                    hover:scale-105 transition-transform"
                    breakLinkClassName="w-full h-full flex items-center justify-center"
                />

                <div className="flex items-center gap-2 hover:scale-105 transition-transform">
                    <label htmlFor="size-select" className="text-sm text-gray-600"></label>
                    <select
                        id="size-select"
                        value={size}
                        onChange={handleSizeChange}
                        className="border rounded px-2 py-1 text-sm"
                    >
                        {[10, 20, 50, 100].map((option) => (
                            <option key={option} value={option}>
                                {option}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            {loading ? (
                <p className="text-gray-600">Cargando libros...</p>
            ) : (
                <BooksTable
                    books={filteredBooks}
                    page={page}
                    size={size}
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                />
            )}

            <EditBookModal
                isOpen={isEditModalOpen}
                book={selectedBook}
                onClose={() => {
                    setFormError(null);
                    handleCloseModal();
                }}
                onSave={handleSave}
                formError={formError}
            />

            <DeleteBookModal
                isOpen={isDeleteModalOpen}
                onClose={() => {
                    setBookToDelete(null);
                    setIsDeleteModalOpen(false);
                }}
                onConfirm={handleConfirmDelete}
                bookTitle={bookToDelete?.title || ""}
            />


        </div>
    );
};

export default BooksPage;
