package com.adi.gab.domain.model;

import com.adi.gab.domain.exception.OrderExceptions;
import com.adi.gab.domain.types.OrderStatus;
import com.adi.gab.domain.valueobject.Address;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.OrderItemId;
import com.adi.gab.domain.valueobject.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
public class Order {

    private OrderId id;
    private UserId customerId;
    private String orderName;
    private Address shippingAddress;
    private Address billingAddress;
    private OrderStatus orderStatus;

    private final List<OrderItem> orderItems = new ArrayList<>();

    public static Order create(OrderId id, UserId customerId, String orderName,
                               Address shippingAddress, Address billingAddress) {

        if (id == null) throw new OrderExceptions.NullOrderArgumentException("OrderId");
        if (customerId == null) throw new OrderExceptions.NullOrderArgumentException("CustomerId");

        return Order
                .builder()
                .id(id)
                .customerId(customerId)
                .orderName(orderName)
                .shippingAddress(shippingAddress)
                .billingAddress(billingAddress)
                .build();
    }

    public void addItem(OrderItemId orderItemId, BookId bookId, String title, int quantity, BigDecimal price) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Price must be positive");

        orderItems.add(new OrderItem(orderItemId, this.id, bookId,title, quantity, price));
    }

    public void removeItem(BookId bookId) {
        orderItems.removeIf(item -> item.getBookId().equals(bookId));
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    public BigDecimal getTotalPrice() {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
