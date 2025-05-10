import React from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import AdminCatalogComponent from '../components/AdminCatalogComponent';
import CreateProductComponent from '../components/CreateProductComponent';
import EditProductComponent from '../components/EditProductComponent';

const dummyProduct = {
    title: 'Libro de Ejemplo',
    author: 'Autor Ejemplo',
    image: 'https://via.placeholder.com/150',
    description: 'Descripción de prueba para el libro.',
};

const AdminPage: React.FC = () => {
    return (
        <div className="p-6 bg-[#F5F5F5] min-h-screen">
            <h1 className="text-3xl font-bold text-[#6A0DAD] mb-6">Panel de Administración</h1>

            <nav className="flex gap-4 mb-6">
                <Link to="/admin/catalogo" className="text-[#6A0DAD] underline">
                    Catálogo
                </Link>
                <Link to="/admin/crear" className="text-[#6A0DAD] underline">
                    Crear Producto
                </Link>
            </nav>

            <Routes>
                <Route path="catalogo" element={<AdminCatalogComponent />} />
                <Route path="crear" element={<CreateProductComponent />} />
                <Route path="editar/:id" element={<EditProductComponent existingProduct={dummyProduct} />} />
                {/* Nota: En una app real, `existingProduct` se cargaría dinámicamente por ID */}
            </Routes>
        </div>
    );
};

export default AdminPage;


