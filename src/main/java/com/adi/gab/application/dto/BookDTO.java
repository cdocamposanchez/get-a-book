package com.adi.gab.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@Setter
public class BookDTO {
    private UUID id;
    private String title;
    private String publisher;
    private String description;
    private String imageUrl;
    private Integer year;
    private Integer quantity;
    private BigDecimal price;
    private Double qualification;
    private String categories;
    private MultipartFile image;
}
