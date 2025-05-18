import type { Address } from './AddressDTO';
import type { OrderItem } from './OrderItemDTO';
import type { OrderStatus } from './OrderStatus';

export interface Order {
    id: string;
    customerId: string;
    orderName: string;
    shippingAddress: Address;
    billingAddress: Address;
    orderStatus: OrderStatus;
    orderItems: OrderItem[];
}
