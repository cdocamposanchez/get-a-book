package com.adi.gab.domain.valueobject;

import com.adi.gab.domain.exception.OrderExceptions;

import java.util.UUID;

public record OrderId(UUID value) {
    public OrderId {
        if (value == null) throw new OrderExceptions.NullOrderArgumentException("OrderId");
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }

    public static OrderId of(UUID value){
        return new OrderId(value);
    }
}
