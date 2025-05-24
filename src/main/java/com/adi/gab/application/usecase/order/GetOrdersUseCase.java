package com.adi.gab.application.usecase.order;

import com.adi.gab.application.dto.OrderDTO;
import com.adi.gab.application.dto.request.PaginationRequest;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.OrderMapper;
import com.adi.gab.domain.types.OrderStatus;
import com.adi.gab.domain.valueobject.OrderId;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.repository.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetOrdersUseCase {

    private final OrderRepository orderRepository;

    public GetOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDTO> execute(PaginationRequest pagination) {
        return orderRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize()))
                .stream()
                .map(OrderMapper::toDomain)
                .map(OrderMapper::toDto)
                .toList();
    }

    public OrderDTO getById(OrderId orderId) {
        return orderRepository.findById(orderId.value())
                .map(OrderMapper::toDomain)
                .map(OrderMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderId.value(), this.getClass().getSimpleName()));
    }

    public List<OrderDTO> getByCustomerId(UserId customerId, PaginationRequest pagination) {
        return orderRepository.findByCustomerId(customerId.value(), PageRequest.of(pagination.getPage(), pagination.getSize()))
                .stream()
                .map(OrderMapper::toDomain)
                .map(OrderMapper::toDto)
                .toList();
    }

    @Transactional
    public List<OrderDTO> getByOrderStatus(OrderStatus status, PaginationRequest pagination) {
        return orderRepository.findByOrderStatus(status.toString(), PageRequest.of(pagination.getPage(), pagination.getSize()))
                .stream()
                .map(OrderMapper::toDomain)
                .map(OrderMapper::toDto)
                .toList();
    }
}
