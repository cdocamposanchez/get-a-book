package com.adi.gab.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class BookDTO {
    private UUID id;
    private String title;
    private String publisher;
    private String description;
    private Integer year;
    private Integer quantity;
    private Double price;
    private Double qualification;
    private String categories;
}
