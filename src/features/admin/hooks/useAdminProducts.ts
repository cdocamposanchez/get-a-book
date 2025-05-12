import { useState } from 'react';

interface Product {
    id: string;
    title: string;
    description: string;
    price: number;
    stock: number;
    imageUrl: string;
}

export const useAdminProducts = (initialProducts: Product[] = []) => {
    const [products, setProducts] = useState<Product[]>(initialProducts);
    const [loading, setLoading] = useState(false);

    const handleDelete = async (productId: string) => {
        try {
            setLoading(true);
            const token = localStorage.getItem('token');

            const response = await fetch(`/products/${productId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('No se pudo eliminar el producto');
            }

            setProducts(products.filter(product => product.id !== productId));
        } catch (error) {
            console.error('Error al eliminar el producto:', error);
        } finally {
            setLoading(false);
        }
    };

    return { products, loading, handleDelete };
};

