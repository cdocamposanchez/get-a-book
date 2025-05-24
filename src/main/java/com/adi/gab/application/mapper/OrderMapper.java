package com.adi.gab.application.mapper;

import com.adi.gab.application.dto.OrderDTO;
import com.adi.gab.application.dto.OrderItemDTO;
import com.adi.gab.domain.model.Order;
import com.adi.gab.domain.types.OrderStatus;
import com.adi.gab.domain.valueobject.Address;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.OrderItemId;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.OrderEntity;
import com.adi.gab.infrastructure.persistance.embeddable.AddressEmbeddable;

import java.time.LocalDateTime;

import static com.adi.gab.domain.utils.Functions.DATE_FORMATTER;

public class OrderMapper {
    private OrderMapper(){}

    public static OrderEntity toEntity(Order order) {
        OrderEntity entity = OrderEntity
                .builder()
                .id(order.getId().value())
                .customerId(order.getCustomerId().value())
                .orderStatus(order.getOrderStatus().toString())
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
                .creationDate(order.getCreationDate() != null ? LocalDateTime.parse(order.getCreationDate(), DATE_FORMATTER) : null)
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

        Order order = createOrderBase(OrderId.of(entity.getId()), UserId.of(entity.getCustomerId()), shipping, billing);
        order.setOrderStatus(OrderStatus.fromStringIgnoreCase(entity.getOrderStatus()));
        order.setCreationDate(entity.getCreationDate() != null ? entity.getCreationDate().format(DATE_FORMATTER) : null);

        entity.getItems().forEach(itemEntity ->
                order.addItem(OrderItemId.of(itemEntity.getId()), BookId.of(itemEntity.getBookId()), itemEntity.getTitle(), itemEntity.getQuantity(), itemEntity.getPrice()));

        return order;
    }

    public static Order toDomain(OrderDTO dto, OrderId orderId, UserId customerId) {
        Address shipping = AddressMapper.toDomain(dto.getShippingAddress());
        Address billing = AddressMapper.toDomain(dto.getBillingAddress());

        Order order = createOrderBase(orderId, customerId, shipping, billing);
        order.setOrderStatus(dto.getOrderStatus());
        order.setCreationDate(dto.getCreationDate());

        dto.getOrderItems().forEach(item ->
                order.addItem(OrderItemId.of(item.getId()), BookId.of(item.getBookId()), item.getTitle(), item.getQuantity(), item.getPrice()));

        return order;
    }

    public static OrderDTO toDto(Order order) {
        return OrderDTO.builder()
                .id(order.getId().value())
                .customerId(order.getCustomerId().value())
                .shippingAddress(AddressMapper.toDTO(order.getShippingAddress()))
                .billingAddress(AddressMapper.toDTO(order.getBillingAddress()))
                .orderStatus(order.getOrderStatus())
                .orderItems(order.getOrderItems().stream()
                        .map(item -> OrderItemDTO.builder()
                                .id(item.getId().value())
                                .bookId(item.getBookId().value())
                                .orderId(item.getOrderId().value())
                                .title(item.getTitle())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build())
                        .toList()
                )
                .creationDate(order.getCreationDate())
                .build();
    }

    public static OrderDTO toDto(OrderEntity orderEntity) {
        return OrderDTO.builder()
                .id(orderEntity.getId())
                .customerId(orderEntity.getCustomerId())
                .shippingAddress(AddressMapper.toDto(orderEntity.getShippingAddress()))
                .billingAddress(AddressMapper.toDto(orderEntity.getBillingAddress()))
                .orderStatus(OrderStatus.fromStringIgnoreCase(orderEntity.getOrderStatus()))
                .orderItems(orderEntity.getItems().stream()
                        .map(item -> OrderItemDTO.builder()
                                .id(item.getId())
                                .bookId(item.getBookId())
                                .orderId(item.getOrder().getId())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build())
                        .toList())
                .creationDate(orderEntity.getCreationDate() != null ? orderEntity.getCreationDate().format(DATE_FORMATTER) : null)
                .build();
    }

    private static Order createOrderBase(OrderId id, UserId customerId,
                                         Address shippingAddress, Address billingAddress) {
        return Order.create(
                id,
                customerId,
                shippingAddress,
                billingAddress
        );
    }
}
