import React from "react";
import type { CartItem } from "../../../types/cartItem";
import {FaTrash} from "react-icons/fa";

interface CartItemCardProps {
    item: CartItem;
    onUpdate: (updatedItem: CartItem) => void;
    onRemove: (id: string) => void;
}

const CartItemCard: React.FC<CartItemCardProps> = ({
                                                       item,
                                                       onUpdate,
                                                       onRemove,
                                                   }) => {
    const handleIncrease = () => {
        const updatedItem = { ...item, quantity: item.quantity + 1 };
        updateCartItem(updatedItem);
    };

    const handleDecrease = () => {
        if (item.quantity > 1) {
            const updatedItem = { ...item, quantity: item.quantity - 1 };
            updateCartItem(updatedItem);
        }
    };

    const handleRemove = () => {
        const cart = getCart();
        const updatedCart = cart.filter((i) => i.id !== item.id);
        localStorage.setItem("cart", JSON.stringify(updatedCart));
        onRemove(item.id);
    };

    const updateCartItem = (updatedItem: CartItem) => {
        const cart = getCart();
        const updatedCart = cart.map((i) =>
            i.id === updatedItem.id ? updatedItem : i
        );
        localStorage.setItem("cart", JSON.stringify(updatedCart));
        onUpdate(updatedItem);
    };

    const getCart = (): CartItem[] =>
        JSON.parse(localStorage.getItem("cart") ?? "[]");

    return (
        <div className="flex items-center justify-between bg-white rounded-xl shadow-md p-4 mb-4">
            <div className="flex items-center gap-4">
                <img
                    src={item.image}
                    alt={item.title}
                    className="w-24 h-32 object-cover rounded"
                />
                <div>
                    <h3 className="text-lg font-bold text-gray-800">{item.title}</h3>
                    <p className="text-sm text-gray-600">
                        Precio unitario: ${item.price.toFixed(2)}
                    </p>
                    <p className="text-sm text-gray-700">
                        Subtotal: ${(item.price * item.quantity).toFixed(2)}
                    </p>
                </div>
            </div>
            <div className="flex items-center gap-2">
                <button
                    onClick={handleDecrease}
                    className="px-3 py-1 rounded bg-gray-200 text-gray-800 hover:bg-gray-300 text-lg font-bold"
                >
                    −
                </button>
                <span className="text-md font-semibold">{item.quantity}</span>
                <button
                    onClick={handleIncrease}
                    className="px-3 py-1 rounded bg-gray-200 text-gray-800 hover:bg-gray-300 text-lg font-bold"
                >
                    +
                </button>
                <button
                    onClick={handleRemove}
                    className="ml-4 px-3 py-1 rounded bg-red-500 text-white hover:bg-red-600 font-semibold"
                >
                    <FaTrash className="h-7"/>
                </button>
            </div>
        </div>
    );
};

export default CartItemCard;
