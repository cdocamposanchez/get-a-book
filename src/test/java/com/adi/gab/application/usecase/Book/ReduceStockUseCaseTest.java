package com.adi.gab.application.usecase.Book;

import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.usecase.book.ReduceStockUseCase;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReduceStockUseCaseTest {

    private BookRepository bookRepository;
    private ReduceStockUseCase reduceStockUseCase;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        reduceStockUseCase = new ReduceStockUseCase(bookRepository);
    }

    @Test
    void shouldReduceStockSuccessfully() {
        // Arrange
        UUID bookIdValue = UUID.randomUUID();
        BookId bookId = new BookId(bookIdValue);
        int initialStock = 10;
        int quantityToReduce = 3;

        BookEntity bookEntity = BookEntity.builder()
                .id(bookIdValue)
                .title("Test Book")
                .quantity(initialStock)
                .description("Test")
                .price(BigDecimal.TEN)
                .year(2023)
                .imageUrl("image.jpg")
                .categories("Test")
                .publisher("Publisher")
                .qualification(4.5)
                .build();

        when(bookRepository.findById(bookIdValue)).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any(BookEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        BookDTO result = reduceStockUseCase.execute(bookId, quantityToReduce);

        // Assert
        assertEquals(initialStock - quantityToReduce, result.getQuantity());
        verify(bookRepository).findById(bookIdValue);
        verify(bookRepository).save(bookEntity);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenBookDoesNotExist() {
        // Arrange
        UUID bookIdValue = UUID.randomUUID();
        BookId bookId = new BookId(bookIdValue);

        when(bookRepository.findById(bookIdValue)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            reduceStockUseCase.execute(bookId, 1);
        });

        assertTrue(ex.getMessage().contains("Book not found with ID: " + bookIdValue));
        verify(bookRepository).findById(bookIdValue);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void shouldThrowApplicationExceptionWhenStockIsInsufficient() {
        // Arrange
        UUID bookIdValue = UUID.randomUUID();
        BookId bookId = new BookId(bookIdValue);
        int currentStock = 2;
        int quantityToReduce = 5;

        BookEntity bookEntity = BookEntity.builder()
                .id(bookIdValue)
                .title("Stocked Book")
                .quantity(currentStock)
                .description("Stock test")
                .price(BigDecimal.TEN)
                .year(2023)
                .imageUrl("image.jpg")
                .categories("Test")
                .publisher("Publisher")
                .qualification(4.5)
                .build();

        when(bookRepository.findById(bookIdValue)).thenReturn(Optional.of(bookEntity));

        // Act & Assert
        ApplicationException ex = assertThrows(ApplicationException.class, () -> {
            reduceStockUseCase.execute(bookId, quantityToReduce);
        });

        assertTrue(ex.getMessage().contains("Not enough items in book with ID: " + bookIdValue));
        verify(bookRepository).findById(bookIdValue);
        verify(bookRepository, never()).save(any());
    }
}
