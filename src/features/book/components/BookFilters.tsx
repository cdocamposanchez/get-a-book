import React, { type FC, useEffect } from "react";

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
        <div className="text-sm font-sans text-black">
            <h2 className="text-xl font-bold mb-4">Filtros</h2>

            <div className="mb-4">
                <h3 className="font-semibold">Categoría</h3>
                {categories.map(cat => (
                    <div key={cat} className="ml-2">
                        <label>
                            <input
                                type="checkbox"
                                value={cat}
                                checked={category === cat}
                                onChange={handleCategoryChange}
                                className="mr-1"
                            />
                            {cat}
                        </label>
                    </div>
                ))}
                <div className="ml-2">
                    <label>
                        <input
                            type="checkbox"
                            value=""
                            checked={!category}
                            onChange={() => setCategory(undefined)}
                            className="mr-1"
                        />Sin categoría
                    </label>
                </div>
            </div>

            <hr className="my-2 border-gray-400" />

            <div className="mb-4">
                <h3 className="font-semibold">Ordenar</h3>
                <div className="ml-2">
                    <label>
                        <input
                            type="radio"
                            name="sortOrder"
                            value="ASC"
                            checked={sortOrder === "ASC"}
                            onChange={handleSortChange}
                            className="mr-1"
                        />Ascendente
                    </label>
                </div>
                <div className="ml-2">
                    <label>
                        <input
                            type="radio"
                            name="sortOrder"
                            value="DESC"
                            checked={sortOrder === "DESC"}
                            onChange={handleSortChange}
                            className="mr-1"
                        />Descendente
                    </label>
                </div>
            </div>

            <hr className="my-2 border-gray-400" />

            <div className="mb-4">
                <h3 className="font-semibold">Precio</h3>
                <div className="flex flex-col gap-2 ml-2">
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
                    <span className="text-right text-xs">{`$${minPrice?.toFixed(1)} - $${maxPrice?.toFixed(1)}`}</span>
                </div>
            </div>

            <hr className="my-2 border-gray-400" />

            <div className="mb-4">
                <h3 className="font-semibold">Año</h3>
                <div className="ml-2">
                    <input
                        type="text"
                        inputMode="numeric"
                        pattern="\d*"
                        value={year?.toString() ?? ""}
                        onChange={handleYearChange}
                        placeholder="Ej: 2020"
                        className="border rounded px-2 py-1 w-full text-sm"
                    />
                </div>
            </div>

            <button
                onClick={handleReset}
                className="mt-4 bg-gray-200 hover:bg-gray-300 text-black py-1 px-3 rounded"
            >
                Reiniciar filtros
            </button>
        </div>
    );
};

export default BookFilters;
