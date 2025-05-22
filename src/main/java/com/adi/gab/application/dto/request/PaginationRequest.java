package com.adi.gab.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequest {
    private int page;
    private int size;

    public PaginationRequest(int page, int size) {
        this.page = Math.max(page, 0);
        this.size = Math.max(size, 1);
    }
}
