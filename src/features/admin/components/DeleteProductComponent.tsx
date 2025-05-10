import React from 'react';

interface Props {
    onConfirm: () => void;
    onCancel: () => void;
}

const DeleteProductComponent: React.FC<Props> = ({ onConfirm, onCancel }) => {
    return (
        <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
            <div className="bg-white p-6 rounded-lg shadow-xl max-w-sm text-center">
                <h3 className="text-lg font-semibold mb-4 text-[#6A0DAD]">¿Eliminar producto?</h3>
                <p className="mb-6 text-sm text-gray-600">Esta acción no se puede deshacer.</p>
                <div className="flex justify-center gap-4">
                    <button
                        onClick={onCancel}
                        className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                    >
                        Cancelar
                    </button>
                    <button
                        onClick={onConfirm}
                        className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
                    >
                        Eliminar
                    </button>
                </div>
            </div>
        </div>
    );
};

export default DeleteProductComponent;
