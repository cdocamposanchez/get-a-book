package com.adi.gab.application.usecase.book;
import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.dto.request.BookFilterRequest;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import com.adi.gab.infrastructure.persistance.specification.BookSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GetBooksUseCaseTest {

    private BookRepository bookRepository;
    private GetBooksUseCase getBooksUseCase;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        getBooksUseCase = new GetBooksUseCase(bookRepository);
    }

    private BookEntity sampleBookEntity() {
        BookEntity entity = new BookEntity();
        entity.setId(UUID.randomUUID());
        entity.setTitle("Sample Book");
        entity.setDescription("A great book");
        entity.setPublisher("Sample Publisher");
        entity.setCategories("Tech");
        entity.setYear(2023);
        entity.setPrice(BigDecimal.valueOf(19.99));
        entity.setQualification(4.8);
        entity.setQuantity(10);
        entity.setImageUrl("url");
        return entity;
    }

    @Test
    void shouldReturnPaginatedBooks() {
        // Arrange
        BookEntity entity = sampleBookEntity();
        List<BookEntity> entities = List.of(entity);
        Page<BookEntity> page = new PageImpl<>(entities, PageRequest.of(0, 10), entities.size());

        when(bookRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        BookFilterRequest request = new BookFilterRequest(0, 10);
        request.setSortOrder("ASC");

        // Act
        var result = getBooksUseCase.execute(request);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Sample Book", result.getContent().get(0).getTitle());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getTotalElements());

        verify(bookRepository).findAll(any(Specification.class), eq(PageRequest.of(0, 10, Sort.by("title").ascending())));
    }

    @Test
    void shouldReturnBookById_WhenExists() {
        // Arrange
        BookEntity entity = sampleBookEntity();
        when(bookRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        // Act
        BookDTO result = getBooksUseCase.getById(new BookId(entity.getId()));

        // Assert
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getTitle(), result.getTitle());
    }

    @Test
    void shouldThrowNotFoundException_WhenBookByIdDoesNotExist() {
        // Arrange
        UUID idValue = UUID.randomUUID();
        BookId bookId = new BookId(idValue);
        when(bookRepository.findById(idValue)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException ex = assertThrows(NotFoundException.class, () -> getBooksUseCase.getById(bookId));

        // Construye la cadena esperada exactamente del toString()
        String expectedMessagePart = "Id not found with id" + bookId.toString();

        assertTrue(ex.getMessage().contains(expectedMessagePart));
    }
}