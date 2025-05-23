import React, { type FC, useEffect } from "react";
import { IoReloadOutline } from "react-icons/io5";

interface BookFiltersProps {
    category: string | undefined;
    setCategory: (category: string | undefined) => void;
    sortOrder: 'ASC' | 'DESC';
    setSortOrder: (order: 'ASC' | 'DESC') => void;
    minPrice: number | undefined;
    setMinPrice: (price: number) => void;
    maxPrice: number | undefined;
    setMaxPrice: (price: number) => void;
    year: number | undefined;
    setYear: (year: number | undefined) => void;
}

const categories = ["Ficción", "Terror", "Romance", "Comedia", "TestBook"];

const BookFilters: FC<BookFiltersProps> = ({
                                               category,
                                               setCategory,
                                               sortOrder,
                                               setSortOrder,
                                               minPrice = 0,
                                               setMinPrice,
                                               maxPrice = 400,
                                               setMaxPrice,
                                               year,
                                               setYear
                                           }) => {
    useEffect(() => {
        if (!sortOrder) setSortOrder("ASC");
    }, [sortOrder, setSortOrder]);

    const handleCategoryChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setCategory(e.target.checked ? e.target.value : undefined);
    };

    const handleSortChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSortOrder(e.target.value as 'ASC' | 'DESC');
    };

    const handlePriceChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseFloat(e.target.value);
        if (e.target.name === "min") setMinPrice(value);
        else setMaxPrice(value);
    };

    const handleYearChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const rawValue = e.target.value;
        if (/^\d{0,4}$/.test(rawValue)) {
            const parsed = parseInt(rawValue, 10);
            setYear(isNaN(parsed) ? undefined : parsed);
        }
    };

    const handleReset = () => {
        setCategory(undefined);
        setSortOrder("ASC");
        setMinPrice(0);
        setMaxPrice(400);
        setYear(undefined);
    };

    return (
        <div className="text-sm font-sans text-black p-4 rounded-xl shadow-md bg-white space-y-5">
            <div className="flex items-center justify-between">
                <h2 className="text-xl font-bold">Filtros</h2>
                <button
                    onClick={handleReset}
                    className="text-red-700 hover:text-red-800 hover:scale-110 transition-transform"
                    title="Reiniciar filtros"
                >
                    <IoReloadOutline size={24} />
                </button>
            </div>

            {/* Categoría */}
            <div>
                <h3 className="font-semibold mb-2">Categoría</h3>
                <div className="flex flex-wrap gap-3 ml-1">
                    {categories.map(cat => (
                        <label key={cat} className="flex items-center gap-1">
                            <input
                                type="checkbox"
                                value={cat}
                                checked={category === cat}
                                onChange={handleCategoryChange}
                                className="accent-blue-600"
                            />
                            {cat}
                        </label>
                    ))}
                    <label className="flex items-center gap-1">
                        <input
                            type="checkbox"
                            value=""
                            checked={!category}
                            onChange={() => setCategory(undefined)}
                            className="accent-blue-600"
                        />
                        Sin categoría
                    </label>
                </div>
            </div>

            <hr className="border-gray-300" />

            {/* Orden */}
            <div>
                <h3 className="font-semibold mb-2">Ordenar</h3>
                <div className="gap-5 ml-1">
                    <label className="flex items-center gap-1">
                        <input
                            type="radio"
                            name="sortOrder"
                            value="ASC"
                            checked={sortOrder === "ASC"}
                            onChange={handleSortChange}
                            className="accent-blue-600"
                        />Ascendente
                    </label>
                    <label className="flex items-center gap-1">
                        <input
                            type="radio"
                            name="sortOrder"
                            value="DESC"
                            checked={sortOrder === "DESC"}
                            onChange={handleSortChange}
                            className="accent-blue-600"
                        />Descendente
                    </label>
                </div>
            </div>

            <hr className="border-gray-300" />

            {/* Precio */}
            <div>
                <h3 className="font-semibold mb-2">Precio</h3>
                <div className="flex flex-col gap-3">
                    <input
                        type="range"
                        name="min"
                        min={0}
                        max={400}
                        step={1}
                        value={minPrice}
                        onChange={handlePriceChange}
                        className="accent-blue-600"
                    />
                    <input
                        type="range"
                        name="max"
                        min={0}
                        max={400}
                        step={1}
                        value={maxPrice}
                        onChange={handlePriceChange}
                        className="accent-blue-600"
                    />
                    <div className="text-xs text-right text-gray-600">
                        ${minPrice?.toFixed(1)} - ${maxPrice?.toFixed(1)}
                    </div>
                </div>
            </div>

            <hr className="border-gray-300" />

            {/* Año */}
            <div>
                <h3 className="font-semibold mb-2">Año</h3>
                <input
                    type="text"
                    inputMode="numeric"
                    pattern="\d*"
                    value={year?.toString() ?? ""}
                    onChange={handleYearChange}
                    placeholder="Ej: 2020"
                    className="border border-gray-300 rounded px-3 py-2 w-full text-sm focus:outline-none focus:ring focus:ring-blue-300"
                />
            </div>
        </div>
    );
};

export default BookFilters;
