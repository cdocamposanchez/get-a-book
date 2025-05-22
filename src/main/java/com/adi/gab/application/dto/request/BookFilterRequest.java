package com.adi.gab.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookFilterRequest extends PaginationRequest {
    private String category;
    private String publisher;
    private Integer year;
    private String titleRegex;
    private Double minPrice;
    private Double maxPrice;
    private String sortOrder;

    public BookFilterRequest(int page, int size) {
        super(page, size);
    }
}