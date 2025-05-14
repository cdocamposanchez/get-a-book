package com.adi.gab.application.usecase.Book;

import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.BookMapper;
import com.adi.gab.application.usecase.book.UpdateBookUseCase;
import com.adi.gab.domain.model.Book;
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

class UpdateBookUseCaseTest {

    private BookRepository bookRepository;
    private UpdateBookUseCase updateBookUseCase;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        updateBookUseCase = new UpdateBookUseCase(bookRepository);
    }

    @Test
    void shouldUpdateBookSuccessfully() {
        // Arrange
        UUID id = UUID.randomUUID();
        BookId bookId = BookId.of(id);

        BookEntity existingEntity = BookMapper.toEntity(sampleBook(bookId));
        BookDTO updatedDto = sampleBookDTO(id);

        Book updatedDomain = Book.update(BookMapper.toDomain(existingEntity), BookMapper.toDomain(updatedDto));
        BookEntity savedEntity = BookMapper.toEntity(updatedDomain);

        when(bookRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(savedEntity);

        // Act
        BookDTO result = updateBookUseCase.execute(bookId, updatedDto);

        // Assert
        assertEquals("New Title", result.getTitle());
        verify(bookRepository).findById(id);
        verify(bookRepository).save(any(BookEntity.class));
    }

    private BookDTO sampleBookDTO(UUID id) {
        return BookDTO.builder()
                .id(id)
                .title("New Title")
                .publisher("Publisher")
                .description("Description")
                .price(BigDecimal.valueOf(10))
                .year(2022)
                .imageUrl("image.png")
                .quantity(5)
                .qualification(4.5)
                .categories("Fiction")
                .image(null)
                .build();
    }

    @Test
    void shouldThrowNotFoundExceptionWhenBookDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();
        BookId bookId = new BookId(id);
        BookDTO bookDto = sampleBookDTO();

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            updateBookUseCase.execute(bookId, bookDto);
        });

        assertTrue(exception.getMessage().contains("Book not found with ID:"));
        verify(bookRepository).findById(id);
        verify(bookRepository, never()).save(any());
    }

    // Helpers

    private Book sampleBook(BookId id) {
        return Book.builder()
                .id(id)
                .title("Old Title")
                .publisher("Publisher")
                .description("Description")
                .price(BigDecimal.valueOf(10))
                .year(2022)
                .imageUrl("image.png")
                .quantity(5)
                .qualification(4.5)
                .categories("Fiction")
                .build();
    }

    private BookDTO sampleBookDTO() {
        return BookDTO.builder()
                .title("Any Title")
                .publisher("Publisher")
                .description("Description")
                .price(BigDecimal.valueOf(10))
                .year(2022)
                .imageUrl("image.png")
                .quantity(5)
                .qualification(4.5)
                .categories("Fiction")
                .image(null)
                .build();
    }
}
