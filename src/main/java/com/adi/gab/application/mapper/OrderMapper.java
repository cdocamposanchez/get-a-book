package com.adi.gab.application.mapper;

import com.adi.gab.application.dto.OrderDTO;
import com.adi.gab.application.dto.OrderItemDTO;
import com.adi.gab.domain.model.Order;
import com.adi.gab.domain.valueobject.Address;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.OrderEntity;
import com.adi.gab.infrastructure.persistance.embeddable.AddressEmbeddable;

public class OrderMapper {
    private OrderMapper(){}

    public static OrderEntity toEntity(Order order) {
        OrderEntity entity = OrderEntity
                .builder()
                .id(order.getId().value())
                .customerId(order.getCustomerId().value())
                .orderName(order.getOrderName())
                .orderStatus(order.getOrderStatus())
                .shippingAddress(AddressEmbeddable.builder()
                        .firstName(order.getShippingAddress().getFirstName())
                        .lastName(order.getShippingAddress().getLastName())
                        .emailAddress(order.getShippingAddress().getEmailAddress())
                        .addressLine(order.getShippingAddress().getAddressLine())
                        .city(order.getShippingAddress().getCity())
                        .country(order.getShippingAddress().getCountry())
                        .zipCode(order.getShippingAddress().getZipCode())
                        .build())
                .billingAddress(AddressEmbeddable.builder()
                        .firstName(order.getBillingAddress().getFirstName())
                        .lastName(order.getBillingAddress().getLastName())
                        .emailAddress(order.getBillingAddress().getEmailAddress())
                        .addressLine(order.getBillingAddress().getAddressLine())
                        .city(order.getBillingAddress().getCity())
                        .country(order.getBillingAddress().getCountry())
                        .zipCode(order.getBillingAddress().getZipCode())
                        .build())
                .build();

        order.getOrderItems().forEach(orderItem ->
                entity.addItem(
                        OrderItemMapper.toEntity(orderItem, entity)
                )
        );

        return entity;
    }

    public static Order toDomain(OrderEntity entity) {
        Address shipping = AddressMapper.toDomain(entity.getShippingAddress());
        Address billing = AddressMapper.toDomain(entity.getBillingAddress());

        Order order = createOrderBase(OrderId.of(entity.getId()), UserId.of(entity.getCustomerId()), entity.getOrderName(), shipping, billing);
        order.setOrderStatus(entity.getOrderStatus());

        entity.getItems().forEach(itemEntity ->
                order.addItem(BookId.of(itemEntity.getBookId()), itemEntity.getQuantity(), itemEntity.getPrice()));

        return order;
    }

    public static Order toDomain(OrderDTO dto, OrderId orderId, UserId customerId) {
        Address shipping = AddressMapper.toDomain(dto.getShippingAddress());
        Address billing = AddressMapper.toDomain(dto.getBillingAddress());

        Order order = createOrderBase(orderId, customerId, dto.getOrderName(), shipping, billing);
        order.setOrderStatus(dto.getOrderStatus());

        dto.getOrderItems().forEach(item ->
                order.addItem(BookId.of(item.getBookId()), item.getQuantity(), item.getPrice()));

        return order;
    }

    public static OrderDTO toDto(Order order) {
        return OrderDTO.builder()
                .id(order.getId().value())
                .customerId(order.getCustomerId().value())
                .orderName(order.getOrderName())
                .shippingAddress(AddressMapper.toDTO(order.getShippingAddress()))
                .billingAddress(AddressMapper.toDTO(order.getBillingAddress()))
                .orderStatus(order.getOrderStatus())
                .orderItems(order.getOrderItems().stream()
                        .map(item -> OrderItemDTO.builder()
                                .id(item.getId().value())
                                .bookId(item.getBookId().value())
                                .orderId(item.getOrderId().value())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build())
                        .toList()
                )
                .build();
    }

    private static Order createOrderBase(OrderId id, UserId customerId, String orderName,
                                         Address shippingAddress, Address billingAddress) {
        return Order.create(
                id,
                customerId,
                orderName,
                shippingAddress,
                billingAddress
        );
    }
}
