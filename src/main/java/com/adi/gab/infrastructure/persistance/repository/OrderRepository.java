package com.adi.gab.infrastructure.persistance.repository;

import com.adi.gab.infrastructure.persistance.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByCustomerId(UUID customerId, Pageable pageable);

    Page<OrderEntity> findByOrderNameContainingIgnoreCase(String orderName, Pageable pageable);

    Page<OrderEntity> findByOrderStatus(String status, Pageable pageable);
}
