package com.adi.gab.infrastructure.persistance.repository;

import com.adi.gab.infrastructure.persistance.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}
