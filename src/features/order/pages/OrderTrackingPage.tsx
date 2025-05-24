import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import orderService from "../OrderService.ts";
import type { Order } from "../../../types/order";

const steps = [
    { label: "Orden Pagada", key: "PAID" },
    { label: "Orden en Camino", key: "SHIPPED" },
    { label: "Order Completa", key: "COMPLETED" }
];

const OrderTrackingPage: React.FC = () => {
    const { orderId } = useParams();
    const navigate = useNavigate();
    const [order, setOrder] = useState<Order | null>(null);

    useEffect(() => {
        if (orderId) {
            orderService.getOrderById(orderId).then(setOrder);
        }
    }, [orderId]);

    const getCurrentStep = () => {
        return steps.findIndex((step) => step.key === order?.orderStatus);
    };

    if (!order) return <div className="p-10 text-white">Cargando...</div>;

    return (
        <div className="min-h-screen bg-[#6e9c9f] flex items-center justify-center px-4 py-8">
            <div className="bg-[#e0e0e0] p-6 rounded-md w-full max-w-3xl shadow-md">
                <button onClick={() => navigate(-1)}
                        className="text-black text-xl mb-3 hover:scale-110 transition-transform rounded">&larr;</button>
                <h2 className="font-semibold text-gray-800 mb-4">
                    SEGUIMIENTO DE LA ORDEN: <span className="text-purple-700 font-bold">{order.id}</span>
                </h2>

                <div className="flex justify-between items-center relative mb-6">
                    {steps.map((step, index) => {
                        const currentStep = getCurrentStep();
                        const isCompleted = index <= currentStep;
                        return (
                            <div key={step.label} className="flex flex-col items-center w-full text-xs text-center">
                                <div
                                    className={`w-6 h-6 rounded-full border-2 ${
                                        isCompleted ? "bg-purple-600 border-purple-600" : "bg-gray-300 border-gray-300"
                                    } mb-2`}
                                />
                                <span>{step.label}</span>
                            </div>
                        );
                    })}
                    <div className="absolute top-3 left-0 w-full h-1 bg-gray-300 z-0" />
                    <div
                        className="absolute top-3 left-0 h-1 bg-purple-600 z-10"
                        style={{ width: `${(getCurrentStep() / (steps.length - 1)) * 100}%` }}
                    />
                </div>
            </div>
        </div>
    );
};

export default OrderTrackingPage;
