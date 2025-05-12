package com.adi.gab.presentation.controller;

import com.adi.gab.application.dto.PaginationRequest;
import com.adi.gab.application.usecase.book.CreateBookUseCase;
import com.adi.gab.application.usecase.book.DeleteBookUseCase;
import com.adi.gab.application.usecase.book.GetBooksUseCase;
import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.dto.ResponseDTO;
import com.adi.gab.application.usecase.book.GetFavoritesUseCase;
import com.adi.gab.application.usecase.book.UpdateBookUseCase;
import com.adi.gab.domain.valueobject.BookId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    private final CreateBookUseCase createBookUseCase;
    private final GetBooksUseCase getBooksUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;
    private final GetFavoritesUseCase getFavoritesUseCase;


    public BookController(CreateBookUseCase createBookUseCase,
                          GetBooksUseCase getBooksUseCase, UpdateBookUseCase updateBookUseCase, DeleteBookUseCase deleteBookUseCase, GetFavoritesUseCase getFavoritesUseCase) {
        this.createBookUseCase = createBookUseCase;
        this.getBooksUseCase = getBooksUseCase;
        this.updateBookUseCase = updateBookUseCase;
        this.deleteBookUseCase = deleteBookUseCase;
        this.getFavoritesUseCase = getFavoritesUseCase;
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

    @PutMapping
    public ResponseEntity<ResponseDTO<BookDTO>> update(@RequestBody BookDTO bookDTO) {

        BookDTO createdDTO = updateBookUseCase.execute(BookId.of(bookDTO.getId()), bookDTO);

        ResponseDTO<BookDTO> response = new ResponseDTO<>(
                "Book successfully updated",
                createdDTO,
                HttpStatus.CREATED
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @DeleteMapping
    public ResponseEntity<ResponseDTO<BookDTO>> delete(@RequestParam UUID bookId) {

        deleteBookUseCase.execute(BookId.of(bookId));

        ResponseDTO<BookDTO> response = new ResponseDTO<>(
                "Book successfully deleted with id: " + bookId,
                HttpStatus.CREATED
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO<List<BookDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationRequest pagination = new PaginationRequest(page, size);
        List<BookDTO> books = getBooksUseCase.execute(pagination);

        ResponseDTO<List<BookDTO>> response = new ResponseDTO<>(
                "Books retrieved successfully",
                books,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Buscar libros por categoría con paginación
    @GetMapping("/category")
    public ResponseEntity<ResponseDTO<List<BookDTO>>> getByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationRequest pagination = new PaginationRequest(page, size);
        List<BookDTO> books = getBooksUseCase.getByCategory(category, pagination);

        ResponseDTO<List<BookDTO>> response = new ResponseDTO<>(
                "Books retrieved by category successfully",
                books,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Buscar libros por publisher con paginación
    @GetMapping("/publisher")
    public ResponseEntity<ResponseDTO<List<BookDTO>>> getByPublisher(
            @RequestParam String publisher,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationRequest pagination = new PaginationRequest(page, size);
        List<BookDTO> books = getBooksUseCase.getByPublisher(publisher, pagination);

        ResponseDTO<List<BookDTO>> response = new ResponseDTO<>(
                "Books retrieved by publisher successfully",
                books,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Buscar libros por año con paginación
    @GetMapping("/year")
    public ResponseEntity<ResponseDTO<List<BookDTO>>> getByYear(
            @RequestParam Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationRequest pagination = new PaginationRequest(page, size);
        List<BookDTO> books = getBooksUseCase.getByYear(year, pagination);

        ResponseDTO<List<BookDTO>> response = new ResponseDTO<>(
                "Books retrieved by year successfully",
                books,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Buscar libros por regex en título o descripción con paginación
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<List<BookDTO>>> searchBooks(
            @RequestParam String titleRegex,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationRequest pagination = new PaginationRequest(page, size);
        List<BookDTO> books = getBooksUseCase.getByRegex(titleRegex, pagination);

        ResponseDTO<List<BookDTO>> response = new ResponseDTO<>(
                "Books retrieved by search successfully",
                books,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Buscar un libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<BookDTO>> getBookById(@PathVariable UUID id) {
        BookDTO book = getBooksUseCase.getById(BookId.of(id));

        ResponseDTO<BookDTO> response = new ResponseDTO<>(
                "Book retrieved successfully",
                book,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/favorites")
    public ResponseEntity<ResponseDTO<List<BookDTO>>> searchBooks(
    ) {
        List<BookDTO> books = getFavoritesUseCase.execute();

        ResponseDTO<List<BookDTO>> response = new ResponseDTO<>(
                "Books retrieved by search successfully",
                books,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}