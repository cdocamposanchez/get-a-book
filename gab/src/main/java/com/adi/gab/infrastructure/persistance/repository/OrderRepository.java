package com.adi.gab.infrastructure.persistance.repository;

import com.adi.gab.domain.types.OrderStatus;
import com.adi.gab.infrastructure.persistance.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByCustomerId(UUID customerId, Pageable pageable);

    List<OrderEntity> findByOrderNameContainingIgnoreCase(String orderName, Pageable pageable);

    List<OrderEntity> findByOrderStatus(OrderStatus status, Pageable pageable);

}
