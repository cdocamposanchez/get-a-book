package com.adi.gab.infrastructure.exception;

import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.domain.exception.DomainException;
import com.adi.gab.application.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Domain Exceptions
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ResponseDTO<String>> handleDomainException(DomainException ex) {
        ResponseDTO<String> response = new ResponseDTO<>(
                "Domain Error",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.badRequest().body(response);
    }

    // Validation Erros With @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<String>> handleValidationException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ResponseDTO<String> response = new ResponseDTO<>(
                "Validation Error",
                errors,
                HttpStatus.BAD_REQUEST
        );

        return ResponseEntity.badRequest().body(response);
    }

    // Generic Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> handleGeneralException(Exception ex) {
        ResponseDTO<String> response = new ResponseDTO<>(
                "Internal Server Error",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO<String>> handleNotFoundException(NotFoundException ex) {
        String detailedMessage = String.format("Not Found Exception in %s: %s", ex.getOriginClass(), ex.getMessage());

        ResponseDTO<String> response = new ResponseDTO<>(
                "Not Found",
                detailedMessage,
                HttpStatus.NOT_FOUND
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseDTO<String>> handleApplicationException(ApplicationException ex) {
        String detailedMessage = String.format("Application error in %s: %s", ex.getOriginClass(), ex.getMessage());

        ResponseDTO<String> response = new ResponseDTO<>(
                "Application Error",
                detailedMessage,
                HttpStatus.BAD_REQUEST
        );

        return ResponseEntity.badRequest().body(response);
    }
}
