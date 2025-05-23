import React, { useEffect, useState } from "react";
import OrderCard from "../components/OrderCard";
import OrderDetailsModal from "../components/OrderDetailsModal";
import type {OrderStatus} from "../../../types/orderStatus";
import type {Order} from "../../../types/order";
import orderService from "../OrderService.ts";

const ORDER_STATUSES: OrderStatus[] = ['PAID', 'SHIPPED', 'COMPLETED', 'RETURNED'];

const OrderPage: React.FC = () => {
  const [orders, setOrders] = useState<Order[]>([]);
  const [selectedOrder, setSelectedOrder] = useState<Order | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const auth = JSON.parse(localStorage.getItem("auth") ?? "{}");
  const customerId = auth?.userId ?? "";

  useEffect(() => {
    orderService.getOrdersByCustomerId(customerId).then(setOrders);
  }, [customerId]);

  console.log(orders);

  const groupedOrders = ORDER_STATUSES.reduce((acc, status) => {
    acc[status] = orders.filter((order) => order.orderStatus === status);
    return acc;
  }, {} as Record<OrderStatus, Order[]>);

  return (
      <div className="max-h-[90vh] bg-[#6e9c9f] px-10 py-6 font-sans overflow-y-auto">
        <div className="min-h-screen bg-[#6e9c9f] px-6 py-4 font-sans text-sm">
          <div className="max-w-full mx-auto">
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-2">
              {ORDER_STATUSES.map((status) => (
                  <div key={status}>
                    <h3 className="text-base text-center font-semibold text-black mb-3">{status}</h3>
                    {groupedOrders[status].map((order) => (
                        <OrderCard
                            key={order.id}
                            order={order}
                            onViewDetails={() => {
                              setSelectedOrder(order);
                              setIsModalOpen(true);
                            }}
                        />
                    ))}
                  </div>
              ))}
            </div>
          </div>
        </div>


        <OrderDetailsModal
            order={selectedOrder}
            isOpen={isModalOpen}
            onClose={() => {
              setIsModalOpen(false);
              setSelectedOrder(null);
            }}
        />
      </div>
  );
};

export default OrderPage;
