const FavoriteBooksPage = () => {
    const favoriteBooks = [
        {
            id: 1,
            title: "El Quijote",
            author: "Miguel de Cervantes",
            image: "/images/quijote.jpg",
        },
        {
            id: 2,
            title: "Cien años de soledad",
            author: "Gabriel García Márquez",
            image: "/images/soledad.jpg",
        },
    ];

    return (
        <div className="min-h-screen bg-gray-100 py-8 px-4">
            <div className="max-w-4xl mx-auto bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-3xl font-semibold text-center mb-6 text-gray-800">Libros Favoritos</h2>
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                    {favoriteBooks.map((book) => (
                        <div key={book.id} className="border p-4 rounded-lg">
                            <img src={book.image} alt={book.title} className="w-full h-48 object-cover rounded-lg mb-4" />
                            <h3 className="text-xl font-semibold">{book.title}</h3>
                            <p className="text-gray-600">{book.author}</p>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default FavoriteBooksPage;
