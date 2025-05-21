package com.adi.gab.application.usecase.book;

import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.config.cloudinary.CloudinaryService;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteBookUseCaseTest {

    private BookRepository bookRepository;
    private CloudinaryService cloudinaryService;
    private DeleteBookUseCase deleteBookUseCase;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        cloudinaryService = mock(CloudinaryService.class);
        deleteBookUseCase = new DeleteBookUseCase(bookRepository, cloudinaryService);
    }

    private BookEntity sampleBookEntity(UUID id) {
        BookEntity book = new BookEntity();
        book.setId(id);
        book.setTitle("Sample");
        book.setDescription("A book");
        book.setPrice(BigDecimal.valueOf(9.99));
        book.setImageUrl("https://cloudinary.com/sample.jpg");
        return book;
    }

    @Test
    void shouldDeleteBookSuccessfully() {
        UUID id = UUID.randomUUID();
        BookEntity book = sampleBookEntity(id);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        deleteBookUseCase.execute(new BookId(id));

        verify(cloudinaryService).deleteImage("https://cloudinary.com/sample.jpg");
        verify(bookRepository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        UUID id = UUID.randomUUID();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            deleteBookUseCase.execute(new BookId(id));
        });

        assertTrue(exception.getMessage().contains("Book not found with ID:"));
        verify(cloudinaryService, never()).deleteImage(any());
        verify(bookRepository, never()).deleteById(any());
    }
}
