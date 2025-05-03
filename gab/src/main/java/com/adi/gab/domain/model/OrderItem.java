package com.adi.gab.domain.model;

import com.adi.gab.domain.exception.OrderItemExceptions;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.OrderItemId;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItem {
    private OrderItemId id;
    private OrderId orderId;
    private BookId bookId;
    private Integer quantity;
    private BigDecimal price;

    public OrderItem(OrderId orderId, BookId bookId, Integer quantity, BigDecimal price) {
        if (orderId == null) throw new OrderItemExceptions.NullOrderItemArgumentException("OrderId");
        if (bookId == null) throw new OrderItemExceptions.NullOrderItemArgumentException("BookId");

        this.id = OrderItemId.generate();
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }
}
