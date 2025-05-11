package com.adi.gab.application.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final String originClass;

    public ApplicationException(String message, String originClass) {
        super(message);
        this.originClass = originClass;
    }
}
