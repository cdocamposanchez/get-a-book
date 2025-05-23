import type { Address } from './address';
import type { OrderItem } from './orderItem';
import type { OrderStatus } from './orderStatus';

export interface Order {
    id: string;
    customerId: string;
    shippingAddress: Address;
    billingAddress: Address;
    orderStatus: OrderStatus;
    orderItems: OrderItem[];
    creationDate?: string;
}
