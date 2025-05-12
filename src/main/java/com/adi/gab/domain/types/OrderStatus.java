package com.adi.gab.domain.types;

public enum OrderStatus {
    PENDING,
    PAID,
    SHIPPED,
    COMPLETED,
    RETURNED;

    public static OrderStatus fromStringIgnoreCase(String value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No such a OrderStatus registered with value: " + value);
    }
}
