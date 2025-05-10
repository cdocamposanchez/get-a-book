import { useParams } from 'react-router-dom';

const BookDetailComponent = () => {
    const { id } = useParams();
    return (
        <div>
            <h2>Detalles del Libro {id}</h2>
            {/* Detalles como imagen, descripción, precio, cantidad */}
        </div>
    );
};

export default BookDetailComponent;
