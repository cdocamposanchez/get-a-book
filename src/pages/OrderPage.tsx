import { useState } from "react";
import { Link } from "react-router-dom";

const OrdersPage = () => {
    const [orders] = useState([
        {
            id: "1",
            date: "2025-05-01",
            status: "En camino",
            total: 29.98,
        },
        {
            id: "2",
            date: "2025-04-25",
            status: "Entregado",
            total: 19.99,
        },
    ]);

    return (
        <div className="min-h-screen bg-gray-100 py-8 px-4">
            <h2 className="text-3xl font-semibold text-center mb-6 text-gray-800">Mis Pedidos</h2>
            <div className="space-y-4">
                {orders.map((order) => (
                    <div
                        key={order.id}
                        className="bg-white p-4 rounded-lg shadow-lg flex justify-between items-center"
                    >
                        <div>
                            <h3 className="text-xl font-bold text-gray-800">Pedido #{order.id}</h3>
                            <p className="text-gray-600">Fecha: {order.date}</p>
                            <p className="text-gray-600">Estado: {order.status}</p>
                        </div>
                        <p className="text-gray-800 font-semibold">${order.total.toFixed(2)}</p>
                        <Link
                            to={`/order/${order.id}`}
                            className="text-blue-600 hover:underline"
                        >
                            Ver detalles
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default OrdersPage;
