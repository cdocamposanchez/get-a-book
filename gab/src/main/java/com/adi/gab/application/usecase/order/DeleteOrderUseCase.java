package com.adi.gab.application.usecase.order;

import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.infrastructure.persistance.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteOrderUseCase {

    private final OrderRepository orderRepository;

    public DeleteOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void execute(UUID orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Order not found with ID: " + orderId, this.getClass().getSimpleName());
        }

        orderRepository.deleteById(orderId);
    }
}
