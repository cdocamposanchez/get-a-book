package com.adi.gab.infrastructure.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    private String title;

    private Integer quantity;

    private BigDecimal price;
}
