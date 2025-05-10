import { useState } from "react";
import { Link } from "react-router-dom";

const CatalogPage = () => {
    const [books] = useState([
        {
            id: "1",
            title: "Cien Años de Soledad",
            author: "Gabriel García Márquez",
            price: 15.99,
            image: "https://via.placeholder.com/150",
        },
        {
            id: "2",
            title: "1984",
            author: "George Orwell",
            price: 12.99,
            image: "https://via.placeholder.com/150",
        },
        {
            id: "3",
            title: "El Principito",
            author: "Antoine de Saint-Exupéry",
            price: 9.99,
            image: "https://via.placeholder.com/150",
        },
    ]);

    return (
        <div className="min-h-screen bg-gray-100 py-8 px-4">
            <h2 className="text-3xl font-semibold text-center mb-6 text-gray-800">Catálogo de Libros</h2>
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
                {books.map((book) => (
                    <div key={book.id} className="bg-white p-4 rounded-lg shadow-lg">
                        <img
                            src={book.image}
                            alt={book.title}
                            className="w-full h-48 object-cover rounded-md mb-4"
                        />
                        <h3 className="text-xl font-bold text-gray-800">{book.title}</h3>
                        <p className="text-gray-600">{book.author}</p>
                        <p className="text-gray-800 font-semibold mt-2">${book.price.toFixed(2)}</p>
                        <Link
                            to={`/book/${book.id}`}
                            className="inline-block mt-4 px-6 py-2 bg-blue-600 text-white rounded-full hover:bg-blue-700 transition"
                        >
                            Ver Detalles
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default CatalogPage;
