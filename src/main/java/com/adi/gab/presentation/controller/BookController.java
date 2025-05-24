package com.adi.gab.presentation.controller;

import com.adi.gab.application.dto.request.BookFilterRequest;
import com.adi.gab.application.dto.response.PageResponse;
import com.adi.gab.application.usecase.book.CreateBookUseCase;
import com.adi.gab.application.usecase.book.DeleteBookUseCase;
import com.adi.gab.application.usecase.book.GetBooksUseCase;
import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.dto.ResponseDTO;
import com.adi.gab.application.usecase.book.GetFavoritesUseCase;
import com.adi.gab.application.usecase.book.UpdateBookUseCase;
import com.adi.gab.domain.valueobject.BookId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO<BookDTO>> create(@ModelAttribute BookDTO bookDTO) {
        BookDTO createdDTO = createBookUseCase.execute(bookDTO);

        ResponseDTO<BookDTO> response = new ResponseDTO<>(
                "Book successfully created",
                createdDTO,
                HttpStatus.CREATED
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO<BookDTO>> update(@ModelAttribute BookDTO bookDTO) {
        BookDTO updatedDTO = updateBookUseCase.execute(BookId.of(bookDTO.getId()), bookDTO);

        ResponseDTO<BookDTO> response = new ResponseDTO<>(
                "Book successfully updated",
                updatedDTO,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
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

    @GetMapping()
    public ResponseEntity<ResponseDTO<PageResponse<BookDTO>>> getFilteredBooks(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String titleRegex,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        BookFilterRequest filter = new BookFilterRequest(page, size);
        filter.setCategory(category);
        filter.setPublisher(publisher);
        filter.setYear(year);
        filter.setTitleRegex(titleRegex);
        filter.setMinPrice(minPrice);
        filter.setMaxPrice(maxPrice);
        filter.setSortOrder(sortOrder);

        PageResponse<BookDTO> pageResponse = getBooksUseCase.execute(filter);

        ResponseDTO<PageResponse<BookDTO>> response = new ResponseDTO<>(
                "Books retrieved with filters successfully",
                pageResponse,
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);
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

    @GetMapping("/id")
    public ResponseEntity<ResponseDTO<BookDTO>> getBookById(
            @RequestParam UUID bookId
    ) {
        BookDTO book = getBooksUseCase.getById(BookId.of(bookId));

        ResponseDTO<BookDTO> response = new ResponseDTO<>(
                "Books retrieved by search successfully",
                book,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}