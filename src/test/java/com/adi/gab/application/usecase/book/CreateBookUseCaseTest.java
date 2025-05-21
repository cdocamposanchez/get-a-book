package com.adi.gab.application.usecase.book;

import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.infrastructure.config.cloudinary.CloudinaryService;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateBookUseCaseTest {

    private BookRepository bookRepository;
    private CloudinaryService cloudinaryService;
    private CreateBookUseCase createBookUseCase;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        cloudinaryService = mock(CloudinaryService.class);
        createBookUseCase = new CreateBookUseCase(bookRepository, cloudinaryService);
    }

    @Test
    void shouldCreateBookSuccessfully_WhenTitleIsUniqueAndImageProvided() {
        // Arrange
        MultipartFile image = mock(MultipartFile.class);
        when(image.isEmpty()).thenReturn(false);

        BookDTO inputDto = BookDTO.builder()
                .title("Clean Architecture")
                .description("Concepts of Clean Architecture")
                .publisher("Prentice Hall")
                .qualification(4.5)
                .categories("Software,Architecture")
                .quantity(10)
                .price(BigDecimal.valueOf(49.99))
                .year(2023)
                .image(image)
                .build();

        when(bookRepository.existsByTitleIgnoreCase("Clean Architecture")).thenReturn(false);
        when(cloudinaryService.uploadImage(image)).thenReturn("https://cloudinary.com/image.jpg");

        // Mock save in repository
        when(bookRepository.save(any(BookEntity.class))).thenAnswer(invocation -> {
            BookEntity entity = invocation.getArgument(0);
            entity.setId(UUID.randomUUID());
            return entity;
        });

        // Act
        BookDTO result = createBookUseCase.execute(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals("Clean Architecture", result.getTitle());
        assertEquals("https://cloudinary.com/image.jpg", result.getImageUrl());
        verify(bookRepository).save(any());
        verify(cloudinaryService).uploadImage(image);
    }

    @Test
    void shouldThrowApplicationException_WhenTitleAlreadyExists() {
        // Arrange
        BookDTO inputDto = BookDTO.builder()
                .title("Existing Book")
                .image(mock(MultipartFile.class))
                .build();

        when(bookRepository.existsByTitleIgnoreCase("Existing Book")).thenReturn(true);

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            createBookUseCase.execute(inputDto);
        });

        assertTrue(exception.getMessage().contains("A book with this title already exists"));
        verify(bookRepository, never()).save(any());
    }

}
