package com.adi.gab.application.usecase.order;

import com.adi.gab.application.dto.OrderDTO;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.OrderMapper;
import com.adi.gab.domain.model.Order;
import com.adi.gab.domain.types.OrderStatus;
import com.adi.gab.infrastructure.persistance.entity.OrderEntity;
import com.adi.gab.infrastructure.persistance.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateOrderStatusUseCase {
    private final OrderRepository orderRepository;

    public UpdateOrderStatusUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO execute(UUID orderId, OrderStatus orderStatus) {

        OrderEntity existingEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderId, this.getClass().getSimpleName()));

        if (orderStatus == null) throw new ApplicationException(
                "Order Status cannot be null",
                this.getClass().getSimpleName());

        existingEntity.setOrderStatus(orderStatus);
        Order savedOrder = OrderMapper.toDomain(orderRepository.save(existingEntity));
        return OrderMapper.toDto(savedOrder);
    }
}