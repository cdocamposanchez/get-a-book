package com.adi.gab.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseDTO<T> {
    private String message;
    private T data;
    private int status;

    public ResponseDTO(String message, T data, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.status = status.value();
    }
}