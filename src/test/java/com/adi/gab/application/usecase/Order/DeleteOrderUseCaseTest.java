package com.adi.gab.application.usecase.Order;

import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.usecase.order.DeleteOrderUseCase;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.infrastructure.persistance.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteOrderUseCaseTest {

    private OrderRepository orderRepository;
    private DeleteOrderUseCase deleteOrderUseCase;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        deleteOrderUseCase = new DeleteOrderUseCase(orderRepository);
    }

    @Test
    void shouldDeleteOrderSuccessfully_WhenOrderExists() {
        UUID uuid = UUID.randomUUID();
        OrderId orderId = OrderId.of(uuid);

        when(orderRepository.existsById(uuid)).thenReturn(true);

        deleteOrderUseCase.execute(orderId);

        verify(orderRepository).existsById(uuid);
        verify(orderRepository).deleteById(uuid);
    }

    @Test
    void shouldThrowNotFoundException_WhenOrderDoesNotExist() {
        UUID uuid = UUID.randomUUID();
        OrderId orderId = OrderId.of(uuid);

        when(orderRepository.existsById(uuid)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            deleteOrderUseCase.execute(orderId);
        });

        assertTrue(exception.getMessage().contains("Order not found with ID"));
        verify(orderRepository).existsById(uuid);
        verify(orderRepository, never()).deleteById(uuid);
    }
}
