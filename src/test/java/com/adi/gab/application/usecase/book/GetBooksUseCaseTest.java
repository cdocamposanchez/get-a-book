package com.adi.gab.application.usecase.book;

import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.dto.PaginationRequest;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
        PaginationRequest pagination = new PaginationRequest(0, 10);
        List<BookEntity> books = List.of(sampleBookEntity());
        Page<BookEntity> page = new PageImpl<>(books);

        when(bookRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        List<BookDTO> result = getBooksUseCase.execute(pagination);

        assertEquals(1, result.size());
        assertEquals("Sample Book", result.getFirst().getTitle());
        verify(bookRepository).findAll(PageRequest.of(0, 10));
    }

    @Test
    void shouldReturnBooksByCategory() {
        PaginationRequest pagination = new PaginationRequest(0, 10);
        when(bookRepository.findByCategoriesContainingIgnoreCase(eq("Tech"), any()))
                .thenReturn(new PageImpl<>(List.of(sampleBookEntity())));

        List<BookDTO> result = getBooksUseCase.getByCategory("Tech", pagination);

        assertFalse(result.isEmpty());
        assertEquals("Tech", result.getFirst().getCategories());
    }

    @Test
    void shouldReturnBooksByPublisher() {
        PaginationRequest pagination = new PaginationRequest(0, 10);
        when(bookRepository.findByPublisherContainingIgnoreCase(eq("Sample Publisher"), any()))
                .thenReturn(new PageImpl<>(List.of(sampleBookEntity())));

        List<BookDTO> result = getBooksUseCase.getByPublisher("Sample Publisher", pagination);

        assertEquals("Sample Publisher", result.getFirst().getPublisher());
    }

    @Test
    void shouldReturnBooksByYear() {
        PaginationRequest pagination = new PaginationRequest(0, 10);
        when(bookRepository.findByYear(eq(2023), any()))
                .thenReturn(new PageImpl<>(List.of(sampleBookEntity())));

        List<BookDTO> result = getBooksUseCase.getByYear(2023, pagination);

        assertEquals(2023, result.getFirst().getYear());
    }

    @Test
    void shouldReturnBooksByTitleRegex() {
        PaginationRequest pagination = new PaginationRequest(0, 10);
        when(bookRepository.findByTitleContainingIgnoreCase(eq("sample"), any()))
                .thenReturn(new PageImpl<>(List.of(sampleBookEntity())));

        List<BookDTO> result = getBooksUseCase.getByRegex("sample", pagination);

        assertEquals("Sample Book", result.getFirst().getTitle());
    }

    @Test
    void shouldReturnBookById_WhenExists() {
        BookEntity entity = sampleBookEntity();
        when(bookRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        BookDTO result = getBooksUseCase.getById(new BookId(entity.getId()));

        assertEquals(entity.getId(), result.getId());
    }

    @Test
    void shouldThrowNotFoundException_WhenBookByIdDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> getBooksUseCase.getById(new BookId(id)));

        assertTrue(ex.getMessage().contains("Id not found"));
    }
}

