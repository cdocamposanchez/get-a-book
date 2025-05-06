package com.adi.gab.infrastructure.mapper;

import com.adi.gab.domain.model.OrderItem;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.OrderItemId;
import com.adi.gab.infrastructure.persistance.entity.OrderItemEntity;

import java.util.List;

public class OrderItemMapper {
    private OrderItemMapper(){}

    public static List<OrderItemEntity> toEntities(List<OrderItem> orderItems, OrderId orderId) {
        return orderItems.stream()
                .map(item -> new OrderItemEntity(
                        item.getId().value(),
                        orderId.value(),
                        item.getBookId().value(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();
    }

    public static List<OrderItem> toDomains(List<OrderItemEntity> entities) {
        return entities.stream()
                .map(entity -> new OrderItem(
                        OrderItemId.of(entity.getId()),
                        OrderId.of(entity.getOrderId()),
                        BookId.of(entity.getBookId()),
                        entity.getQuantity(),
                        entity.getPrice()
                ))
                .toList();
    }
}
