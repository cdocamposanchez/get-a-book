import React from 'react';
import { Link } from 'react-router-dom';

interface Props {
    products: any[];
    onDelete: (id: number) => void;
}

export const ProductList: React.FC<Props> = ({ products, onDelete }) => {
    return (
        <div className="grid gap-4">
            {products.map((product) => (
                <div key={product.id} className="p-4 border rounded shadow">
                    <h3 className="text-xl font-bold">{product.title}</h3>
                    <p>{product.author}</p>
                    <div className="mt-2 flex gap-2">
                        <Link to={`/admin/edit-product/${product.id}`} className="text-blue-600">Editar</Link>
                        <button onClick={() => onDelete(product.id)} className="text-red-600">Eliminar</button>
                    </div>
                </div>
            ))}
        </div>
    );
};
