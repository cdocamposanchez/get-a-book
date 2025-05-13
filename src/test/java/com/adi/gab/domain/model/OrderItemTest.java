package com.adi.gab.domain.model;

import com.adi.gab.domain.exception.OrderItemExceptions;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.OrderItemId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    private OrderItemId orderItemId;
    private OrderId orderId;
    private BookId bookId;

    @BeforeEach
    void setUp() {
        orderItemId = OrderItemId.of(UUID.randomUUID());
        orderId = OrderId.of(UUID.randomUUID());
        bookId = BookId.of(UUID.randomUUID());
    }

    // ---------- TESTS constructor ----------

    @Test
    void shouldCreateOrderItemSuccessfully() {
        OrderItem orderItem = new OrderItem(
                orderItemId,
                orderId,
                bookId,
                "Clean Architecture",
                2,
                BigDecimal.valueOf(44.90)
        );

        assertNotNull(orderItem);
        assertEquals(orderItemId, orderItem.getId());
        assertEquals(orderId, orderItem.getOrderId());
        assertEquals(bookId, orderItem.getBookId());
        assertEquals("Clean Architecture", orderItem.getTitle());
        assertEquals(2, orderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(44.90), orderItem.getPrice());
    }

    @Test
    void shouldThrowWhenOrderIdIsNull() {
        Exception ex = assertThrows(OrderItemExceptions.NullOrderItemArgumentException.class, () ->
                new OrderItem(orderItemId, null, bookId, "Title", 1, BigDecimal.TEN));

        assertEquals("Argument: OrderId Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenBookIdIsNull() {
        Exception ex = assertThrows(OrderItemExceptions.NullOrderItemArgumentException.class, () ->
                new OrderItem(orderItemId, orderId, null, "Title", 1, BigDecimal.TEN));

        assertEquals("Argument: BookId Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldAllowNullTitleQuantityAndPrice() {
        OrderItem orderItem = new OrderItem(orderItemId, orderId, bookId, null, null, null);

        assertNull(orderItem.getTitle());
        assertNull(orderItem.getQuantity());
        assertNull(orderItem.getPrice());
    }
}
