import React from 'react';

const CreateProductComponent: React.FC = () => {
    return (
        <div className="bg-white shadow-lg rounded-xl p-6 w-full max-w-md mx-auto mt-10">
            <h2 className="text-2xl font-bold text-[#6A0DAD] mb-4">Crear Nuevo Libro</h2>
            <form className="flex flex-col gap-4">
                <input
                    type="text"
                    placeholder="Título del libro"
                    className="border border-gray-300 p-2 rounded-md focus:outline-[#6A0DAD]"
                />
                <input
                    type="text"
                    placeholder="Autor"
                    className="border border-gray-300 p-2 rounded-md focus:outline-[#6A0DAD]"
                />
                <input
                    type="url"
                    placeholder="URL de la imagen"
                    className="border border-gray-300 p-2 rounded-md focus:outline-[#6A0DAD]"
                />
                <textarea
                    placeholder="Descripción"
                    className="border border-gray-300 p-2 rounded-md h-24 resize-none focus:outline-[#6A0DAD]"
                />
                <button
                    type="submit"
                    className="bg-[#6A0DAD] text-white py-2 rounded-md hover:bg-[#5500aa]"
                >
                    Crear Producto
                </button>
            </form>
        </div>
    );
};

export default CreateProductComponent;

