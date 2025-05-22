import { useMemo, useState } from "react";
import BookCard from "../components/BookCard.tsx";
import BookFilters from "../components/BookFilters.tsx";
import { useBooks } from "../hooks/useBook.ts";
import { useSearch } from "../../../context/SearchContext.tsx";
import Spinner from "../../../components/Spinner.tsx";

const CatalogPage = () => {
    const [category, setCategory] = useState<string | undefined>(undefined);
    const [sortOrder, setSortOrder] = useState<'ASC' | 'DESC'>('ASC');
    const [maxPrice, setMaxPrice] = useState<number | undefined>(400.0);
    const [minPrice, setMinPrice] = useState<number | undefined>(0);
    const [year, setYear] = useState<number | undefined>(undefined);
    const { searchTerm } = useSearch();
    const titleRegex = searchTerm.length > 0 ? searchTerm : undefined;


    const filters = useMemo(() => ({
        category,
        sortOrder,
        minPrice,
        maxPrice,
        year,
        titleRegex
    }), [category, sortOrder,minPrice, maxPrice, year, titleRegex]);

    const {
        books,
        loading,
        error,
        page,
        nextPage,
        prevPage,
    } = useBooks(filters);


    return (
        <div className="flex w-full h-full font-sans">
            <aside className="w-56 bg-green-200 p-5">
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

            <div className="flex flex-col flex-1 p-5 relative bg-[#80AFAB]">
                {loading && <Spinner />}
                {error && (
                    <div className="flex justify-center items-center h-full backdrop-blur-xs">
                        <div className="w-12 h-12 border-4 border-green-600 border-t-transparent rounded-full animate-spin"></div>
                    </div>
                )}

                <div className="overflow-auto mb-20">
                    {!loading && <BookCard books={books} />}
                </div>

                <div
                    className="fixed bottom-0 left-[224px] right-0 bg-[#80AFAB] p-5 flex justify-center gap-4 shadow-inner border-t border-gray-300"
                >
                    <button
                        onClick={prevPage}
                        disabled={page === 0}
                        className="bg-white text-green-700 font-semibold py-2 px-6 rounded-full border border-green-600 disabled:opacity-50 disabled:cursor-not-allowed hover:bg-green-100 transition"
                    >
                        Anterior
                    </button>
                    <button
                        onClick={nextPage}
                        className="bg-white text-green-700 font-semibold py-2 px-6 rounded-full border border-green-600 hover:bg-green-100 transition"
                    >
                        Siguiente
                    </button>
                </div>
            </div>
        </div>
    );
};

export default CatalogPage;
