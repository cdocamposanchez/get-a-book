package com.adi.gab.infrastructure.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false, length = 2048)
    private String description;

    private String imageUrl;

    private Integer year;

    private Integer quantity;

    private BigDecimal price;

    private Double qualification;

    private String categories;
}