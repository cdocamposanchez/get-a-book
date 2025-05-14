package com.adi.gab.application.usecase.Order;

import com.adi.gab.application.dto.OrderDTO;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.OrderMapper;
import com.adi.gab.application.usecase.order.UpdateOrderStatusUseCase;
import com.adi.gab.domain.types.OrderStatus;
import com.adi.gab.infrastructure.persistance.embeddable.AddressEmbeddable;
import com.adi.gab.infrastructure.persistance.entity.OrderEntity;
import com.adi.gab.infrastructure.persistance.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateOrderStatusUseCaseTest {

    private OrderRepository orderRepository;
    private UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        updateOrderStatusUseCase = new UpdateOrderStatusUseCase(orderRepository);
    }

    @Test
    void shouldUpdateOrderStatusSuccessfully() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderStatus newStatus = OrderStatus.SHIPPED;

        AddressEmbeddable address = new AddressEmbeddable();
        address.setFirstName("John");
        address.setLastName("Doe");
        address.setAddressLine("123 Main St");
        address.setEmailAddress("john@example.com");
        address.setCity("Metropolis");
        address.setZipCode("12345");
        address.setCountry("Country");

        OrderEntity entity = new OrderEntity();
        entity.setId(orderId);
        entity.setCustomerId(customerId);
        entity.setOrderStatus(OrderStatus.PENDING.toString());
        entity.setShippingAddress(address);
        entity.setBillingAddress(address);
        entity.setItems(new ArrayList<>());

        OrderEntity savedEntity = new OrderEntity();
        savedEntity.setId(orderId);
        savedEntity.setCustomerId(customerId);
        savedEntity.setOrderStatus(newStatus.toString());
        savedEntity.setShippingAddress(address);
        savedEntity.setBillingAddress(address);
        savedEntity.setItems(new ArrayList<>());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedEntity);

        // Act
        OrderDTO result = updateOrderStatusUseCase.execute(orderId, newStatus);

        // Assert
        assertEquals(newStatus, result.getOrderStatus());
        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    void shouldThrowApplicationExceptionWhenOrderStatusIsNull() {
        // Arrange
        UUID orderId = UUID.randomUUID();

        AddressEmbeddable address = new AddressEmbeddable();
        address.setFirstName("John");
        address.setLastName("Doe");

        OrderEntity entity = new OrderEntity();
        entity.setId(orderId);
        entity.setOrderStatus(OrderStatus.PENDING.toString());
        entity.setBillingAddress(address);
        entity.setShippingAddress(address);
        entity.setId(UUID.randomUUID());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

        // Act + Assert
        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            updateOrderStatusUseCase.execute(orderId, null);
        });

        assertTrue(exception.getMessage().contains("Order Status cannot be null"));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenOrderDoesNotExist() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act + Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            updateOrderStatusUseCase.execute(orderId, OrderStatus.PENDING);
        });

        assertTrue(exception.getMessage().contains("Order not found with ID:"));
        verify(orderRepository).findById(orderId);
        verify(orderRepository, never()).save(any());
    }
}
