type Props = {
    onCategoryChange: (category: string) => void;
    onSortChange: (order: 'asc' | 'desc') => void;
    onPriceChange: (price: number) => void;
};

const BookFilters = ({ onCategoryChange, onSortChange, onPriceChange }: Props) => {
    return (
        <aside className="filters text-sm">
            <h2 className="text-lg font-bold mb-3">Filtros</h2>

            <h3 className="font-semibold mb-1">Categoría</h3>
            <ul className="space-y-1 mb-4">
                {['Ficción', 'Drama', 'Romance', 'Terror', 'Comedia'].map(cat => (
                    <li
                        key={cat}
                        onClick={() => onCategoryChange(cat)}
                        className="cursor-pointer hover:underline hover:text-green-700 transition"
                    >
                        {cat}
                    </li>
                ))}
            </ul>

            <h3 className="font-semibold mb-1">Ordenar</h3>
            <ul className="space-y-1 mb-4">
                <li
                    onClick={() => onSortChange('asc')}
                    className="cursor-pointer hover:underline hover:text-green-700 transition"
                >
                    Ascendente
                </li>
                <li
                    onClick={() => onSortChange('desc')}
                    className="cursor-pointer hover:underline hover:text-green-700 transition"
                >
                    Descendente
                </li>
            </ul>

            <h3 className="font-semibold mb-1">Precio</h3>
            <input
                type="range"
                min="31000"
                max="400000"
                onChange={(e) => onPriceChange(Number(e.target.value))}
                className="w-full mb-1"
            />
            <p className="text-xs text-gray-600">$31.000 - $400.000</p>
        </aside>
    );
};

export default BookFilters;

