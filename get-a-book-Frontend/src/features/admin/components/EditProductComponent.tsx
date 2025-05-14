import React from 'react';

interface Props {
    existingProduct: {
        title: string;
        author: string;
        image: string;
        description: string;
    };
}

const EditProductComponent: React.FC<Props> = ({ existingProduct }) => {
    return (
        <div className="bg-white shadow-lg rounded-xl p-6 w-full max-w-md mx-auto mt-10">
            <h2 className="text-2xl font-bold text-[#6A0DAD] mb-4">Editar Libro</h2>
            <form className="flex flex-col gap-4">
                <input
                    type="text"
                    defaultValue={existingProduct.title}
                    className="border border-gray-300 p-2 rounded-md focus:outline-[#6A0DAD]"
                />
                <input
                    type="text"
                    defaultValue={existingProduct.author}
                    className="border border-gray-300 p-2 rounded-md focus:outline-[#6A0DAD]"
                />
                <input
                    type="url"
                    defaultValue={existingProduct.image}
                    className="border border-gray-300 p-2 rounded-md focus:outline-[#6A0DAD]"
                />
                <textarea
                    defaultValue={existingProduct.description}
                    className="border border-gray-300 p-2 rounded-md h-24 resize-none focus:outline-[#6A0DAD]"
                />
                <button
                    type="submit"
                    className="bg-[#6A0DAD] text-white py-2 rounded-md hover:bg-[#5500aa]"
                >
                    Guardar Cambios
                </button>
            </form>
        </div>
    );
};

export default EditProductComponent;

