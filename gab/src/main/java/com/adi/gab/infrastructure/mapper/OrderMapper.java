package com.adi.gab.infrastructure.mapper;

import com.adi.gab.domain.model.Order;
import com.adi.gab.domain.valueobject.Address;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.OrderEntity;
import com.adi.gab.infrastructure.persistance.embeddable.AddressEmbeddable;

public class OrderMapper {

    public static OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId().value());
        entity.setCustomerId(order.getCustomerId().value());
        entity.setOrderName(order.getOrderName());
        entity.setOrderStatus(order.getStatus());

        AddressEmbeddable shippingAddress = AddressEmbeddable.builder()
                .firstName(order.getShippingAddress().getFirstName())
                .lastName(order.getShippingAddress().getLastName())
                .emailAddress(order.getShippingAddress().getEmailAddress())
                .addressLine(order.getShippingAddress().getAddressLine())
                .city(order.getShippingAddress().getCity())
                .country(order.getShippingAddress().getCountry())
                .zipCode(order.getShippingAddress().getZipCode())
                .build();

        entity.setShippingAddress(shippingAddress);

        AddressEmbeddable billingAddress = AddressEmbeddable.builder()
                .firstName(order.getBillingAddress().getFirstName())
                .lastName(order.getBillingAddress().getLastName())
                .emailAddress(order.getBillingAddress().getEmailAddress())
                .addressLine(order.getBillingAddress().getAddressLine())
                .city(order.getBillingAddress().getCity())
                .country(order.getBillingAddress().getCountry())
                .zipCode(order.getBillingAddress().getZipCode())
                .build();

        entity.setBillingAddress(billingAddress);

        entity.setItems(OrderItemMapper.toEntities(order.getOrderItems(), order.getId()));

        return entity;
    }

    public static Order toDomain(OrderEntity entity) {
        Order order = new Order();
        order.update(
                entity.getOrderName(),
                Address.builder()
                        .firstName(entity.getShippingAddress().getFirstName())
                        .lastName(entity.getShippingAddress().getLastName())
                        .emailAddress(entity.getShippingAddress().getEmailAddress())
                        .addressLine(entity.getShippingAddress().getAddressLine())
                        .city(entity.getShippingAddress().getCity())
                        .country(entity.getShippingAddress().getCountry())
                        .zipCode(entity.getShippingAddress().getZipCode())
                        .build(),
                Address.builder()
                        .firstName(entity.getBillingAddress().getFirstName())
                        .lastName(entity.getBillingAddress().getLastName())
                        .emailAddress(entity.getBillingAddress().getEmailAddress())
                        .addressLine(entity.getBillingAddress().getAddressLine())
                        .city(entity.getBillingAddress().getCity())
                        .country(entity.getBillingAddress().getCountry())
                        .zipCode(entity.getBillingAddress().getZipCode())
                        .build(),
                entity.getOrderStatus()
        );

        order.setId(new OrderId(entity.getId()));
        order.setCustomerId(new UserId(entity.getCustomerId()));
        order.getOrderItems().addAll(OrderItemMapper.toDomains(entity.getItems()));

        return order;
    }
}
