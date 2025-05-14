import React from 'react';

const mockProducts = [
    {
        id: 1,
        title: 'Cien Años de Soledad',
        author: 'Gabriel García Márquez',
        image: 'https://via.placeholder.com/150',
    },
    {
        id: 2,
        title: 'Rayuela',
        author: 'Julio Cortázar',
        image: 'https://via.placeholder.com/150',
    },
];

interface ProductCardComponentProps {
    product?: { id: number; title: string; author: string; image: string } | {
        id: number;
        title: string;
        author: string;
        image: string
    }
}

function ProductCardComponent({}: ProductCardComponentProps) {
    return null;
}

const AdminCatalogComponent: React.FC = () => {
    return (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
            {mockProducts.map((product) => (
                <ProductCardComponent key={product.id} product={product}/>
            ))}
        </div>
    );
};

export default AdminCatalogComponent;
