package com.adi.gab.domain.valueobject;

import com.adi.gab.domain.exception.OrderExceptions;

import java.util.UUID;

public record OrderItemId(UUID value) {
    public OrderItemId {
        if (value == null) throw new OrderExceptions.NullOrderArgumentException("OrderItemId");
    }

    public static OrderItemId generate() {
        return new OrderItemId(UUID.randomUUID());
    }

    public static OrderItemId of(UUID value){
        return new OrderItemId(value);
    }
}
