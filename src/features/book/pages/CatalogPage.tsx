import { useMemo, useState } from "react";
import BookCard from "../components/BookCard.tsx";
import BookFilters from "../components/BookFilters.tsx";
import { useBooks } from "../hooks/useBook.ts";
import { useSearch } from "../../../context/SearchContext.tsx";
import Spinner from "../../../components/Spinner.tsx";

const CatalogPage = () => {
    const [category, setCategory] = useState<string | undefined>(undefined);
    const [sortOrder, setSortOrder] = useState<"ASC" | "DESC">("ASC");
    const [maxPrice, setMaxPrice] = useState<number | undefined>(400.0);
    const [minPrice, setMinPrice] = useState<number | undefined>(0);
    const [year, setYear] = useState<number | undefined>(undefined);
    const { searchTerm } = useSearch();
    const titleRegex = searchTerm.length > 0 ? searchTerm : undefined;

    const filters = useMemo(
        () => ({
            category,
            sortOrder,
            minPrice,
            maxPrice,
            year,
            titleRegex,
        }),
        [category, sortOrder, minPrice, maxPrice, year, titleRegex]
    );

    const { books, loading, error, page, nextPage, prevPage } = useBooks(filters);

    return (
        <div className="min-h-screen bg-[#80AFAB] font-sans p-2 flex justify-center items-start">
            <div
                className="flex gap-6"
                style={{maxWidth: '80vw', paddingTop: '2rem', height: '90vh', width: '100%'}}
            >
                {/* Sidebar filters */}
                <aside
                    className="w-1/5 bg-gray-100 rounded-lg p-4 shadow-md sticky top-8 h-fit min-w-[250px] max-h-[80vh] border overflow-y-auto transition-all duration-120 ease-in-out">
                    <BookFilters
                        category={category}
                        setCategory={setCategory}
                        sortOrder={sortOrder}
                        setSortOrder={setSortOrder}
                        minPrice={minPrice}
                        setMinPrice={setMinPrice}
                        maxPrice={maxPrice}
                        setMaxPrice={setMaxPrice}
                        year={year}
                        setYear={setYear}
                    />
                </aside>

                {/* Main content */}
                <main className="border w-full flex flex-col bg-gray-100 rounded-xl shadow-md p-6 max-h-[80vh]">

                    {loading && (
                        <div className="flex justify-center items-center h-64 w-full">
                            <Spinner/>
                        </div>
                    )}

                    {error && (
                        <div className="flex justify-center items-center h-64 text-red-600 font-semibold">
                            Ocurrió un error al cargar los libros.
                        </div>
                    )}

                    {!loading && !error && books.length === 0 && (
                        <p className="text-center text-gray-600 text-lg">
                            No se encontraron libros con esos filtros.
                        </p>
                    )}

                    <div className="max-h-[70vh]">
                        {!loading && books.length > 0 && <BookCard books={books}/>}
                    </div>


                    {/* Pagination */}
                    <div className="mt-4 flex justify-center gap-4 bg-transparent">
                        <button
                            onClick={prevPage}
                            disabled={page === 0}
                            className="bg-green-600 text-white font-semibold py-1 px-6 rounded-full disabled:opacity-50 disabled:cursor-not-allowed hover:bg-green-700 transition"
                        >
                            Anterior
                        </button>
                        <button
                            onClick={nextPage}
                            className="bg-green-600 text-white font-semibold py-1 px-6 rounded-full hover:bg-green-700 transition"
                        >
                            Siguiente
                        </button>
                    </div>
                </main>
            </div>
        </div>
    );
};

export default CatalogPage;
