import { useAdminProducts } from '../hooks/useAdminProducts';
import { useNavigate } from 'react-router-dom';

export const AdminCatalogPage = () => {
    const { products, loading, handleDelete } = useAdminProducts();
    const navigate = useNavigate();

    if (loading) return <p className="text-center mt-10">Cargando productos...</p>;

    return (
        <div className="p-6">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-3xl font-bold">Catálogo de Productos</h1>
                <button
                    className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                    onClick={() => navigate('/admin/create-product')}
                >
                    + Nuevo producto
                </button>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
                {products.map(product => (
                    <div key={product.id} className="bg-white shadow-lg rounded-lg p-4 flex flex-col justify-between">
                        <img
                            src={product.imageUrl || 'https://via.placeholder.com/150'}
                            alt={product.title}
                            className="h-48 w-full object-cover rounded-md mb-4"
                        />
                        <h2 className="text-xl font-semibold mb-2">{product.title}</h2>
                        <p className="text-gray-700 mb-2">{product.description}</p>
                        <p className="text-sm text-gray-500 mb-4">Precio: ${product.price}</p>
                        <div className="flex justify-between">
                            <button
                                className="text-white bg-green-600 px-3 py-1 rounded hover:bg-green-700"
                                onClick={() => navigate(`/admin/edit-product/${product.id}`)}
                            >
                                Editar
                            </button>
                            <button
                                className="text-white bg-red-600 px-3 py-1 rounded hover:bg-red-700"
                                onClick={() => handleDelete(product.id)}
                            >
                                Eliminar
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};


export default class AdminPage {
}