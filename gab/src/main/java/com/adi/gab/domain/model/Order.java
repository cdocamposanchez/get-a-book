package com.adi.gab.domain.model;

import com.adi.gab.domain.exception.OrderExceptions;
import com.adi.gab.domain.types.OrderStatus;
import com.adi.gab.domain.valueobject.Address;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.UserId;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Order {

    private OrderId id;
    private UserId customerId;
    private String orderName;
    private Address shippingAddress;
    private Address billingAddress;
    private OrderStatus status = OrderStatus.PENDING;

    private final List<OrderItem> orderItems = new ArrayList<>();

    public Order() {}

    private Order(OrderId id, UserId customerId, String orderName,
                  Address shippingAddress, Address billingAddress) {
        if (id == null) throw new OrderExceptions.NullOrderArgumentException("OrderId");
        if (customerId == null) throw new OrderExceptions.NullOrderArgumentException("CustomerId");

        this.id = id;
        this.customerId = customerId;
        this.orderName = orderName;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
    }

    public static Order create(OrderId id, UserId customerId, String orderName,
                               Address shippingAddress, Address billingAddress) {
        return new Order(id, customerId, orderName, shippingAddress, billingAddress);
    }

    public void update(String orderName, Address shippingAddress, Address billingAddress, OrderStatus status) {
        this.orderName = orderName;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.status = status;
    }

    public void addItem(BookId bookId, int quantity, BigDecimal price) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Price must be positive");

        orderItems.add(new OrderItem(this.id, bookId, quantity, price));
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
