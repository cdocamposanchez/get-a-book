import React from 'react';

interface Product {
    id: number;
    title: string;
    author: string;
    image: string;
}

interface Props {
    product: Product;
}

const ProductCardComponent: React.FC<Props> = ({ product }) => {
    return (
        <div className="bg-white shadow-md rounded-xl p-4 flex flex-col items-center">
            <img
                src={product.image}
                alt={product.title}
                className="w-32 h-32 object-cover rounded-lg mb-4"
            />
            <h3 className="text-lg font-semibold text-center">{product.title}</h3>
            <p className="text-sm text-gray-600">{product.author}</p>
            <div className="flex gap-3 mt-4">
                <button className="bg-[#6A0DAD] text-white px-4 py-1 rounded-md text-sm hover:bg-[#5500aa]">
                    Editar
                </button>
                <button className="bg-red-500 text-white px-4 py-1 rounded-md text-sm hover:bg-red-600">
                    Eliminar
                </button>
            </div>
        </div>
    );
};

export default ProductCardComponent;
