type Props = {
    onCategoryChange: (category: string) => void;
    onSortChange: (order: "asc" | "desc") => void;
    onPriceChange: (price: number) => void;
};

const AdminFilters = ({ onCategoryChange, onSortChange, onPriceChange }: Props) => {
    return (
        <div className="p-4 bg-white shadow rounded">
            <h2 className="text-xl font-semibold mb-4">Filtros</h2>

            <div className="mb-4">
                <h3 className="font-semibold mb-2">Categoría</h3>
                <ul className="space-y-1">
                    {["Ficción", "Drama", "Romance", "Terror", "Comedia"].map((cat) => (
                        <li
                            key={cat}
                            onClick={() => onCategoryChange(cat)}
                            className="cursor-pointer hover:underline hover:text-blue-600"
                        >
                            {cat}
                        </li>
                    ))}
                </ul>
            </div>

            <div className="mb-4">
                <h3 className="font-semibold mb-2">Ordenar por precio</h3>
                <ul className="space-y-1">
                    <li
                        onClick={() => onSortChange("asc")}
                        className="cursor-pointer hover:underline hover:text-blue-600"
                    >
                        Ascendente
                    </li>
                    <li
                        onClick={() => onSortChange("desc")}
                        className="cursor-pointer hover:underline hover:text-blue-600"
                    >
                        Descendente
                    </li>
                </ul>
            </div>

            <div className="mb-4">
                <h3 className="font-semibold mb-2">Precio máximo</h3>
                <input
                    type="range"
                    min="31000"
                    max="400000"
                    step="1000"
                    onChange={(e) => onPriceChange(Number(e.target.value))}
                    className="w-full"
                />
                <p className="text-sm text-gray-600 mt-1">$31.000 - $400.000</p>
            </div>
        </div>
    );
};

export default AdminFilters;

