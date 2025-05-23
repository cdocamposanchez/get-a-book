import React from "react";
import type {OrderItem} from "../../../types/orderItem";

interface OrderItemCardProps {
    item: OrderItem;
}

const OrderItemCard: React.FC<OrderItemCardProps> = ({ item }) => {
    return (
        <div className="relative flex items-start bg-white rounded-lg shadow-lg border-1 p-3 mb-2 min-h-[100px]">
            <div className="text-sm text-gray-800 space-y-1 pr-24">
                <h3 className="font-extrabold">{item.title}</h3>
                <p>Cantidad: {item.quantity}</p>
            </div>
            <div className="absolute bottom-2 right-3 flex gap-6 text-sm font-semibold text-gray-700">
                <p>Precio: ${item.price.toFixed(2)}</p>
                <p>Subtotal: ${(item.price * item.quantity).toFixed(2)}</p>
            </div>
        </div>
    );
};

export default OrderItemCard;
