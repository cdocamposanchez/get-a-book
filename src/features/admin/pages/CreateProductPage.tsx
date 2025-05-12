import { useState } from 'react';

export const CreateProductPage = () => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!title || !description || !price) {
            setError('Todos los campos son obligatorios.');
            return;
        }

        const newProduct = { title, description, price };

        try {
            setLoading(true);
            setError(null);

            // Enviar el nuevo producto al backend usando fetch
            const response = await fetch('/products', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newProduct),
            });

            if (!response.ok) {
                throw new Error('No se pudo crear el producto');
            }

            const data = await response.json();
            console.log('Producto creado:', data);
            // Limpiar el formulario después de la creación
            setTitle('');
            setDescription('');
            setPrice('');
        } catch (error) {
            console.error('Error creando el producto:', error);
            setError('Hubo un error al crear el producto.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="max-w-lg mx-auto p-6">
            <h2 className="text-2xl font-semibold mb-6">Crear Producto</h2>

            {error && <div className="text-red-600 mb-4">{error}</div>}

            <form onSubmit={handleSubmit} className="space-y-4">
                <label className="block">
                    Título:
                    <input
                        type="text"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                        className="w-full border p-2 rounded mt-2"
                    />
                </label>

                <label className="block">
                    Descripción:
                    <input
                        type="text"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                        className="w-full border p-2 rounded mt-2"
                    />
                </label>

                <label className="block">
                    Precio:
                    <input
                        type="number"
                        value={price}
                        onChange={(e) => setPrice(e.target.value)}
                        required
                        className="w-full border p-2 rounded mt-2"
                    />
                </label>

                <button
                    type="submit"
                    disabled={loading}
                    className={`w-full py-2 text-white rounded mt-4 ${loading ? 'bg-gray-400' : 'bg-blue-600 hover:bg-blue-700'}`}
                >
                    {loading ? 'Creando...' : 'Crear Producto'}
                </button>
            </form>
        </div>
    );
};

