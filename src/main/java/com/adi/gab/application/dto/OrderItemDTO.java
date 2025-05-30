package com.adi.gab.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public  class OrderItemDTO {
    private UUID id;
    private UUID orderId;
    private UUID bookId;
    private String title;
    private Integer quantity;
    private BigDecimal price;
}