package com.adi.gab.presentation.controller;

import com.adi.gab.application.usecase.book.CreateBookUseCase;
import com.adi.gab.infrastructure.dto.BookDTO;
import com.adi.gab.infrastructure.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final CreateBookUseCase createBookUseCase;

    public BookController(CreateBookUseCase createBookUseCase) {
        this.createBookUseCase = createBookUseCase;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<BookDTO>> create(@RequestBody BookDTO bookDTO) {

        BookDTO createdDTO = createBookUseCase.execute(bookDTO);

        ResponseDTO<BookDTO> response = new ResponseDTO<>(
                "Book successfully created",
                createdDTO,
                HttpStatus.CREATED
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}