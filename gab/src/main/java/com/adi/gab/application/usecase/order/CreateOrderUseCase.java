package com.adi.gab.application.usecase.order;

import com.adi.gab.infrastructure.dto.OrderDTO;
import com.adi.gab.domain.model.Order;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.mapper.OrderMapper;
import com.adi.gab.infrastructure.persistance.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;

    public CreateOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(OrderDTO dto) {
        OrderId orderId = OrderId.generate();
        Order order = Order.create(orderId, UserId.of(dto.getCustomerId()), dto.getOrderName(), dto.getShippingAddress(), dto.getBillingAddress());
        orderRepository.save(OrderMapper.toEntity(order));
        return order;
    }
}
