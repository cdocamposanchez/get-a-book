import React from "react";
import type {Order} from "../../../types/order";
import BaseModal from "../../../components/Modal.tsx";
import OrderItemCard from "./OrderItemCard.tsx";

interface OrderDetailsModalProps {
    order: Order | null;
    isOpen: boolean;
    onClose: () => void;
}

const OrderDetailsModal: React.FC<OrderDetailsModalProps> = ({ order, isOpen, onClose }) => {
    if (!order) return null;

    return (
        <BaseModal isOpen={isOpen} onClose={onClose}>
            <h2 className="text-xl font-bold mb-4 text-black">Detalle de Orden #{order.id}</h2>
            <div className="text-gray-800 space-y-2">
                <p><strong>Estado:</strong> {order.orderStatus}</p>
                <p><strong>Fecha:</strong> {order.creationDate}</p>
                <p><strong>Productos:</strong></p>
                <div className="mt-2 space-y-2 p-2 max-h-[50vh] overflow-y-auto">
                    {order.orderItems.map((item) => (
                        <OrderItemCard key={item.id} item={item}/>
                    ))}
                </div>
                <strong>Total:</strong> ${order.orderItems.reduce((sum, item) => sum + item.price * item.quantity, 0).toFixed(2)}
            </div>
        </BaseModal>
    );
};

export default OrderDetailsModal;
