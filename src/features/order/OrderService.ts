import axiosInstance from '../../utils/axiosConfig.ts';
import type { Order } from '../../types/order';
import type { Response } from '../../types/response';
import type {OrderStatus} from "../../types/orderStatus";

const orderService = {
    createStripeSession: async (order: Order): Promise<string> => {
        const response = await axiosInstance.post<Response<string>>('/orders/order-session', order);
        return response.data.data;
    },

    confirmOrder: async (order: Order): Promise<Order> => {
        const response = await axiosInstance.post<Response<Order>>('/orders/confirm-order', order);
        return response.data.data;
    },

    updateOrderStatus: async (orderId: string, status: OrderStatus): Promise<Order> => {
        const response = await axiosInstance.put<Response<Order>>(`/orders`, null, {
            params: { orderId, orderStatus: status }
        });
        return response.data.data;
    },

    deleteOrder: async (orderId: string): Promise<void> => {
        await axiosInstance.delete<Response<null>>(`/orders`, { params: { orderId } });
    },

    getAllOrders: async (page = 0, size = 10): Promise<Order[]> => {
        const response = await axiosInstance.get<Response<Order[]>>(`/orders`, {
            params: { page, size }
        });
        return response.data.data;
    },

    getOrderById: async (id: string): Promise<Order> => {
        const response = await axiosInstance.get<Response<Order>>(`/orders/${id}`);
        return response.data.data;
    },

    getOrdersByStatus: async (status: OrderStatus, page = 0, size = 10): Promise<Order[]> => {
        const response = await axiosInstance.get<Response<Order[]>>(`/orders/status`, {
            params: { status, page, size }
        });
        return response.data.data;
    },

    getOrdersByCustomerId: async (customerId: string, page = 0, size = 10): Promise<Order[]> => {
        const response = await axiosInstance.get<Response<Order[]>>(`/orders/customer`, {
            params: { customerId, page, size }
        });
        return response.data.data;
    },

    getOrdersByName: async (orderName: string, page = 0, size = 10): Promise<Order[]> => {
        const response = await axiosInstance.get<Response<Order[]>>(`/orders/name`, {
            params: { orderName, page, size }
        });
        return response.data.data;
    }
};

export default orderService;
