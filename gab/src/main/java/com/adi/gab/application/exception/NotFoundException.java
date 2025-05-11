package com.adi.gab.application.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String originClass;

    public NotFoundException(String message, String originClass) {
        super(message);
        this.originClass = originClass;
    }
}