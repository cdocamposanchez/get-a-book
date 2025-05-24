package com.adi.gab.domain.model;

import com.adi.gab.domain.exception.OrderExceptions;
import com.adi.gab.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private OrderId validOrderId;
    private UserId validCustomerId;
    private BookId validBookId;
    private OrderItemId validOrderItemId;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;

    @BeforeEach
    void setUp() {
        validOrderId = OrderId.generate();
        validCustomerId = UserId.generate();
        validBookId = BookId.of(UUID.randomUUID());
        validOrderItemId = OrderItemId.generate();

        shippingAddress = Address.of(
                "John", "Doe", "john@example.com", "123 Street", "USA", "New York", "10001"
        );

        billingAddress = Address.of(
                "Jane", "Smith", "jane@example.com", "456 Avenue", "USA", "Los Angeles", "90001"
        );

        order = Order.create(validOrderId, validCustomerId, shippingAddress, billingAddress);
    }

    // ---------- TESTS create() ----------

    @Test
    void shouldCreateOrderSuccessfully() {
        assertNotNull(order);
        assertEquals(validOrderId, order.getId());
        assertEquals(validCustomerId, order.getCustomerId());
        assertEquals(shippingAddress, order.getShippingAddress());
        assertEquals(billingAddress, order.getBillingAddress());
        assertTrue(order.getOrderItems().isEmpty());
    }

    @Test
    void shouldThrowWhenOrderIdIsNull() {
        Exception ex = assertThrows(OrderExceptions.NullOrderArgumentException.class, () ->
                Order.create(null, validCustomerId,  shippingAddress, billingAddress));
        assertEquals("Argument: OrderId Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenCustomerIdIsNull() {
        Exception ex = assertThrows(OrderExceptions.NullOrderArgumentException.class, () ->
                Order.create(validOrderId, null, shippingAddress, billingAddress));
        assertEquals("Argument: CustomerId Cannot be null.", ex.getMessage());
    }

    // ---------- TESTS addItem() ----------

    @Test
    void shouldAddItemToOrder() {
        order.addItem(validOrderItemId, validBookId, "Effective Java", 2, BigDecimal.valueOf(39.99));

        assertEquals(1, order.getOrderItems().size());
        OrderItem item = order.getOrderItems().get(0);
        assertEquals(validBookId, item.getBookId());
        assertEquals(2, item.getQuantity());
        assertEquals(BigDecimal.valueOf(39.99), item.getPrice());
    }

    @Test
    void shouldThrowWhenQuantityIsZero() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                order.addItem(validOrderItemId, validBookId, "Effective Java", 0, BigDecimal.valueOf(39.99)));
        assertEquals("Quantity must be positive", ex.getMessage());
    }

    @Test
    void shouldThrowWhenPriceIsZero() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                order.addItem(validOrderItemId, validBookId, "Effective Java", 1, BigDecimal.ZERO));
        assertEquals("Price must be positive", ex.getMessage());
    }

    // ---------- TESTS removeItem() ----------

    @Test
    void shouldRemoveItemByBookId() {
        order.addItem(validOrderItemId, validBookId, "Effective Java", 1, BigDecimal.valueOf(39.99));
        assertEquals(1, order.getOrderItems().size());

        order.removeItem(validBookId);
        assertTrue(order.getOrderItems().isEmpty());
    }

    // ---------- TESTS getTotal() ----------

    @Test
    void shouldReturnTotalPrice() {
        order.addItem(validOrderItemId, validBookId, "Book 1", 2, BigDecimal.valueOf(10.00));
        order.addItem(OrderItemId.generate(), BookId.of(UUID.randomUUID()), "Book 2", 1, BigDecimal.valueOf(30.00));

        BigDecimal total = order.getTotalPrice();
        assertEquals(BigDecimal.valueOf(50.00), total);
    }

    @Test
    void totalPriceShouldBeZeroWhenNoItems() {
        assertEquals(BigDecimal.ZERO, order.getTotalPrice());
    }
}
