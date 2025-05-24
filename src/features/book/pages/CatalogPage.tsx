import { useMemo, useState } from "react";
import ReactPaginate from "react-paginate";
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

    const { books, loading, error, page, totalPages, setPage } = useBooks(filters);

    return (
        <div className="min-h-screen bg-[#80AFAB] font-sans p-2 flex justify-center items-start">
            <div
                className="flex gap-6"
                style={{ maxWidth: "80vw", paddingTop: "2rem", height: "90vh", width: "100%" }}
            >
                <aside
                    className="w-1/5 bg-gray-100 rounded-lg p-4 shadow-md sticky top-8 h-fit min-w-[250px] max-h-[80vh] border overflow-y-auto transition-all duration-120 ease-in-out"
                >
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

                <main className="border w-full flex flex-col bg-gray-100 rounded-xl shadow-md p-6 max-h-[80vh]">
                    {loading && (
                        <div className="flex justify-center items-center h-64 w-full min-h-[100vh]">
                            <Spinner />
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

                    <div className="max-h-[70vh] overflow-y-auto min-h-[50]">
                        {!loading && books.length > 0 && <BookCard books={books} />}
                    </div>

                    <div className="mt-4 flex justify-center">
                        <ReactPaginate
                            previousLabel={"<"}
                            nextLabel={">"}
                            breakLabel={"..."}
                            pageCount={totalPages}
                            marginPagesDisplayed={3}
                            pageRangeDisplayed={3}
                            onPageChange={(event) => {
                                setPage(event.selected);
                            }}
                            forcePage={page}
                            containerClassName="flex gap-1"
                            pageClassName="w-8 h-8 rounded-md font-semibold text-black flex items-center justify-center cursor-pointer"
                            pageLinkClassName="w-full h-full flex items-center justify-center"
                            activeClassName="bg-[#80AFAB] text-black"
                            activeLinkClassName="w-full h-full flex items-center justify-center"
                            previousClassName="w-8 h-8 rounded-md bg-[#80AFAB] text-black font-semibold flex items-center justify-center cursor-pointer"
                            previousLinkClassName="w-full h-full flex items-center justify-center"
                            nextClassName="w-8 h-8 rounded-md bg-[#80AFAB] text-black font-semibold flex items-center justify-center cursor-pointer"
                            nextLinkClassName="w-full h-full flex items-center justify-center"
                            disabledClassName="opacity-50 cursor-not-allowed"
                            breakClassName="w-8 text-center text-black select-none flex items-center justify-center"
                            breakLinkClassName="w-full h-full flex items-center justify-center"
                        />
                    </div>
                </main>
            </div>
        </div>
    );
};

export default CatalogPage;
