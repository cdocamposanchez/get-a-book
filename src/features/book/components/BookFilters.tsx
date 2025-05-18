const BookFilters = () => {
    return (
        <aside className="filters">
            <h2>Filtros</h2>
            <h3>Categoría</h3>
            <ul>
                <li>Ficción</li>
                <li>Drama</li>
                <li>Romance</li>
                <li>Terror</li>
                <li>Comedia</li>
            </ul>

            <h3>Ordenar</h3>
            <ul>
                <li>Ascendente</li>
                <li>Descendente</li>
            </ul>

            <h3>Precio</h3>
            <input type="range" min="31000" max="400000"/>
            <p>$31.000 - $400.000</p>
        </aside>
    );
};

export default BookFilters;
