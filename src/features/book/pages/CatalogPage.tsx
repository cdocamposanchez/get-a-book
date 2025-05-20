import { useState } from "react";
import BookCard from "../components/BookCard.tsx";
import BookFilters from "../components/BookFilters.tsx";
import { useBooks } from "../hooks/useBook.ts";
import Spinner from "../../../components/Spinner.tsx";

const CatalogPage = () => {
    // Estados para filtros
    const [category, setCategory] = useState<string | null>(null);
    const [sortOrder, setSortOrder] = useState<'asc' | 'desc' | null>(null);
    const [maxPrice, setMaxPrice] = useState<number>(400000);

    const {
        books,
        loading,
        error,
        page,
        nextPage,
        prevPage,
    } = useBooks({ category, sortOrder, maxPrice }); // debes modificar useBooks para que acepte estos filtros

    return (
        <div className="flex w-full h-full font-sans">
            <aside className="w-56 bg-green-200 p-5">
                <BookFilters
                    onCategoryChange={setCategory}
                    onSortChange={setSortOrder}
                    onPriceChange={setMaxPrice}
                />
            </aside>

            <div className="flex flex-col flex-1 bg-white p-5 relative">
                {loading && <Spinner />}
                {error && <p className="mb-2 text-red-600">{error}</p>}

                <div className="overflow-auto mb-20">
                    {!loading && <BookCard books={books} />}
                </div>

                <div className="fixed bottom-0 left-[224px] right-0 bg-white p-5 flex justify-center gap-4 border-t border-gray-300">
                    <button
                        onClick={prevPage}
                        disabled={page === 0}
                        className="bg-green-600 text-white py-2 px-5 rounded disabled:opacity-50 disabled:cursor-not-allowed hover:bg-green-700 transition"
                    >
                        Anterior
                    </button>
                    <button
                        onClick={nextPage}
                        className="bg-green-600 text text-white py-2 px-5 rounded hover:bg-green-700 transition"
                    >
                        Siguiente
                    </button>
                </div>
            </div>
        </div>
    );
};

export default CatalogPage;
