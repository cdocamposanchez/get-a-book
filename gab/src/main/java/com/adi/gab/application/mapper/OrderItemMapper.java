package com.adi.gab.application.mapper;

import com.adi.gab.domain.model.OrderItem;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.OrderItemId;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.infrastructure.persistance.entity.OrderEntity;
import com.adi.gab.infrastructure.persistance.entity.OrderItemEntity;

public class OrderItemMapper {

    public static OrderItem toDomain(OrderItemEntity orderItemEntity) {
        if (orderItemEntity == null) {
            return null;
        }

        return OrderItem
                .builder()
                .id(OrderItemId.of(orderItemEntity.getId()))
                .orderId(OrderId.of(orderItemEntity.getOrder().getId()))
                .bookId(BookId.of(orderItemEntity.getBookId()))
                .quantity(orderItemEntity.getQuantity())
                .price(orderItemEntity.getPrice())
                .build();
    }

    public static OrderItemEntity toEntity(OrderItem orderItem, OrderEntity orderEntity) {
        if (orderItem == null) {
            return null;
        }

        return OrderItemEntity
                .builder()
                .id(orderItem.getId().value())
                .order(orderEntity)
                .bookId(orderItem.getBookId().value())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }

    private OrderItemMapper() {}
}
