import React, { useState } from "react";
import AdminFilters from "./AdminFilters";

const mockProducts = [
    {
        id: 1,
        title: "Cien Años de Soledad",
        author: "Gabriel García Márquez",
        image: "https://via.placeholder.com/150",
        category: "Ficción",
        price: 120000,
    },
    {
        id: 2,
        title: "Rayuela",
        author: "Julio Cortázar",
        image: "https://via.placeholder.com/150",
        category: "Drama",
        price: 150000,
    },
];

interface Product {
    id: number;
    title: string;
    author: string;
    image: string;
    category: string;
    price: number;
}

interface ProductCardComponentProps {
    product: Product;
}

function ProductCardComponent({ product }: ProductCardComponentProps) {
    return (
        <div className="border p-4 rounded shadow">
            <img src={product.image} alt={product.title} className="w-full h-40 object-cover rounded" />
            <h3 className="mt-2 font-bold">{product.title}</h3>
            <p className="text-sm">{product.author}</p>
            <p className="text-green-700 font-semibold">${product.price.toLocaleString()}</p>
        </div>
    );
}

const AdminCatalogComponent: React.FC = () => {
    const [category, setCategory] = useState<string | null>(null);
    const [price, setPrice] = useState<number | null>(null);
    const [sortOrder, setSortOrder] = useState<"asc" | "desc" | null>(null);

    // Filtrado básico de mockProducts
    let filteredProducts = [...mockProducts];

    if (category) {
        filteredProducts = filteredProducts.filter(p => p.category === category);
    }

    if (price) {
        filteredProducts = filteredProducts.filter(p => p.price <= price);
    }

    if (sortOrder === "asc") {
        filteredProducts.sort((a, b) => a.price - b.price);
    } else if (sortOrder === "desc") {
        filteredProducts.sort((a, b) => b.price - a.price);
    }

    return (
        <div className="flex gap-4">
            <aside className="w-64">
                <AdminFilters
                    onCategoryChange={(cat) => setCategory(cat)}
                    onPriceChange={(p) => setPrice(p)}
                    onSortChange={(order) => setSortOrder(order)}
                />
            </aside>

            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 flex-1">
                {filteredProducts.length > 0 ? (
                    filteredProducts.map((product) => (
                        <ProductCardComponent key={product.id} product={product} />
                    ))
                ) : (
                    <p>No se encontraron productos</p>
                )}
            </div>
        </div>
    );
};

export default AdminCatalogComponent;
