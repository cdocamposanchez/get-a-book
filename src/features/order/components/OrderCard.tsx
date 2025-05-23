import React from "react";
import type { Order } from "../../../types/order";

interface OrderCardProps {
    order: Order;
    onViewDetails: () => void;
}

const OrderCard: React.FC<OrderCardProps> = ({ order, onViewDetails }) => (
    <div className="flex flex-col items-center justify-between bg-white rounded-xl drop-shadow-2xl border-1 px-4 py-3 mb-3 w-full min-h-[50px]">
        <div className="flex flex-col text-gray-800 text-xs space-y-0.5">
            <span className="text-xs font-bold">{order.id}</span>
            <span><strong>Estado:</strong> {order.orderStatus}</span>
            <span><strong>Fecha:</strong> {order.creationDate}</span>
        </div>

        <div className="flex items-center h-full mt-2">
            <button
                onClick={onViewDetails}
                className="bg-[#4a7074] hover:bg-[#3b5d60] text-white font-medium text-xs px-3 py-1.5 border-1 rounded-md transition-all"
            >
                DETALLES
            </button>
        </div>
    </div>

);


export default OrderCard;
