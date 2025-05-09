import { useState } from "react";

const CartPage = () => {
    const [cartItems, setCartItems] = useState([
        {
            id: 1,
            title: "El Quijote",
            quantity: 1,
            price: 20.00,
        },
        {
            id: 2,
            title: "Cien años de soledad",
            quantity: 2,
            price: 15.50,
        },
    ]);

    const handleRemoveItem = (id: number) => {
        setCartItems(cartItems.filter(item => item.id !== id));
    };

    const total = cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0);

    return (
        <div className="min-h-screen bg-gray-100 py-8 px-4">
            <div className="max-w-4xl mx-auto bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-3xl font-semibold text-center mb-6 text-gray-800">Mi Carrito</h2>
                <div className="space-y-4">
                    {cartItems.map((item) => (
                        <div key={item.id} className="flex justify-between items-center border-b py-4">
                            <div>
                                <h3 className="text-lg font-semibold">{item.title}</h3>
                                <p className="text-gray-600">Cantidad: {item.quantity}</p>
                            </div>
                            <div>
                                <p className="text-gray-600">${item.price.toFixed(2)}</p>
                                <button
                                    onClick={() => handleRemoveItem(item.id)}
                                    className="text-red-600 hover:text-red-800"
                                >
                                    Eliminar
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
                <div className="mt-6 flex justify-between items-center">
                    <h3 className="text-xl font-semibold">Total: ${total.toFixed(2)}</h3>
                    <button className="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700">
                        Proceder al pago
                    </button>
                </div>
            </div>
        </div>
    );
};

export default CartPage;
