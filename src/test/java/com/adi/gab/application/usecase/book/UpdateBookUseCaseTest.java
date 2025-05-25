package com.adi.gab.application.usecase.book;

import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.BookMapper;
import com.adi.gab.domain.model.Book;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.config.cloudinary.CloudinaryService;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateBookUseCaseTest {

    private BookRepository bookRepository;
    private CloudinaryService cloudinaryService;
    private UpdateBookUseCase updateBookUseCase;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        cloudinaryService = mock(CloudinaryService.class);
        updateBookUseCase = new UpdateBookUseCase(bookRepository, cloudinaryService);
    }

    //  Crea un BookDTO con flexibilidad para ID, título e imageUrl
    private BookDTO sampleBookDTO(UUID id, String title, String imageUrl) {
        return BookDTO.builder()
                .id(id)
                .title(title)
                .publisher("Publisher")
                .description("Description")
                .price(BigDecimal.valueOf(10))
                .year(2022)
                .imageUrl(imageUrl)
                .quantity(5)
                .qualification(4.5)
                .categories("Fiction")
                .image(null)
                .build();
    }

    // Helper: Crea un Book (dominio) con flexibilidad para ID y título
    private Book sampleBook(BookId id, String title) {
        return Book.builder()
                .id(id)
                .title(title)
                .publisher("Publisher")
                .description("Description")
                .price(BigDecimal.valueOf(10))
                .year(2022)
                .imageUrl("http://example.com/existing_image.png")
                .quantity(5)
                .qualification(4.5)
                .categories("Fiction")
                .build();
    }

    @Test
    void shouldUpdateBookSuccessfully() {
        // Arrange
        UUID id = UUID.randomUUID();
        BookId bookId = BookId.of(id);

        String oldTitle = "Old Title";
        String newTitle = "New Title";
        String oldImageUrl = "http://example.com/old_image.png";
        String newImageUrl = "http://example.com/new_image.png";

        // Entidad existente con título e imagen antiguos
        BookEntity existingEntity = BookMapper.toEntity(sampleBook(bookId, oldTitle));
        existingEntity.setImageUrl(oldImageUrl);

        // DTO con los datos actualizados, incluyendo la nueva imagen
        BookDTO updatedDto = sampleBookDTO(id, newTitle, null);
        updatedDto.setImage(new MockMultipartFile("image", "new_image.png", "image/png", "new image data".getBytes()));

        // Simular la lógica del UseCase, que es subir la imagen y luego actualiza el imageUrl del DTO.


        // Mocks:
        when(bookRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(bookRepository.existsByTitleIgnoreCase(newTitle)).thenReturn(false);
        when(cloudinaryService.uploadImage(any(MockMultipartFile.class))).thenReturn(newImageUrl);


        Book updatedDomain = Book.update(BookMapper.toDomain(existingEntity), BookMapper.toDomain(sampleBookDTO(id, newTitle, newImageUrl)));
        BookEntity savedEntity = BookMapper.toEntity(updatedDomain);
        when(bookRepository.save(any(BookEntity.class))).thenReturn(savedEntity);


        // Act
        BookDTO result = updateBookUseCase.execute(bookId, updatedDto);

        // Assert
        assertNotNull(result);
        assertEquals(newTitle, result.getTitle());
        assertEquals(newImageUrl, result.getImageUrl());

        // Verificar interacciones:
        verify(bookRepository).findById(id);
        verify(bookRepository).existsByTitleIgnoreCase(newTitle);
        verify(cloudinaryService).deleteImage(oldImageUrl);
        verify(cloudinaryService).uploadImage(any(MockMultipartFile.class));
        verify(bookRepository).save(any(BookEntity.class));
    }


    @Test
    void shouldThrowNotFoundExceptionWhenBookDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();
        BookId bookId = new BookId(id);
        BookDTO bookDto = sampleBookDTO(id, "Any Title", null);

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            updateBookUseCase.execute(bookId, bookDto);
        });

        // Asegurarse de que el mensaje coincide con la implementación del UseCase
        assertTrue(exception.getMessage().contains("Book not found with ID: " + id.toString()));
        verify(bookRepository).findById(id);
        verify(bookRepository, never()).save(any());
        verifyNoInteractions(cloudinaryService);
    }

    @Test
    void shouldThrowApplicationExceptionWhenNewTitleAlreadyExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        BookId bookId = BookId.of(id);
        String oldTitle = "Old Title";
        String duplicateTitle = "Existing Title";
        String oldImageUrl = "http://example.com/old_image.png";

        BookEntity existingEntity = BookMapper.toEntity(sampleBook(bookId, oldTitle));
        existingEntity.setImageUrl(oldImageUrl);

        BookDTO updatedDtoWithDuplicateTitle = sampleBookDTO(id, duplicateTitle, null);

        when(bookRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(bookRepository.existsByTitleIgnoreCase(duplicateTitle)).thenReturn(true);

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            updateBookUseCase.execute(bookId, updatedDtoWithDuplicateTitle);
        });

        assertTrue(exception.getMessage().contains("A book with this title already exists: " + duplicateTitle));
        verify(bookRepository).findById(id);
        verify(bookRepository).existsByTitleIgnoreCase(duplicateTitle);
        verify(bookRepository, never()).save(any());
        verifyNoInteractions(cloudinaryService);
    }

    @Test
    void shouldHandleImageUploadFailure() {
        // Arrange
        UUID id = UUID.randomUUID();
        BookId bookId = BookId.of(id);
        String oldTitle = "Existing Title";
        String newTitle = "Updated Title";
        String oldImageUrl = "http://example.com/old_image.png";

        BookEntity existingEntity = BookMapper.toEntity(sampleBook(bookId, oldTitle));
        existingEntity.setImageUrl(oldImageUrl);

        BookDTO updatedDto = sampleBookDTO(id, newTitle, null);
        updatedDto.setImage(new MockMultipartFile("image", "new_image.png", "image/png", "new image data".getBytes()));

        when(bookRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(bookRepository.existsByTitleIgnoreCase(newTitle)).thenReturn(false);
        when(cloudinaryService.uploadImage(any())).thenThrow(new RuntimeException("Cloudinary upload failed"));

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            updateBookUseCase.execute(bookId, updatedDto);
        });

        assertTrue(exception.getMessage().contains("Failed to upload new image for book with title: " + newTitle));
        verify(bookRepository).findById(id);
        verify(bookRepository).existsByTitleIgnoreCase(newTitle);
        verify(cloudinaryService).deleteImage(oldImageUrl);
        verify(cloudinaryService).uploadImage(any());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void shouldUpdateBookWithoutChangingTitleIfEmpty() {
        // Arrange
        UUID id = UUID.randomUUID();
        BookId bookId = BookId.of(id);

        String existingTitle = "Original Title";
        String existingImageUrl = "http://example.com/existing_image.png";
        String newImageUrl = "http://example.com/updated_image.png";

        BookEntity existingEntity = BookMapper.toEntity(sampleBook(bookId, existingTitle));
        existingEntity.setImageUrl(existingImageUrl);

        // DTO con título vacío y nueva imagen
        BookDTO updatedDto = sampleBookDTO(id, "", null);
        updatedDto.setImage(new MockMultipartFile("image", "new_image.png", "image/png", "new image data".getBytes()));

        // Mocks
        when(bookRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(cloudinaryService.uploadImage(any())).thenReturn(newImageUrl);

        Book updatedDomainWithNewImage = Book.update(
                BookMapper.toDomain(existingEntity),
                BookMapper.toDomain(sampleBookDTO(id, existingTitle, newImageUrl))
        );
        BookEntity savedEntity = BookMapper.toEntity(updatedDomainWithNewImage);
        when(bookRepository.save(any(BookEntity.class))).thenReturn(savedEntity);


        // Act
        BookDTO result = updateBookUseCase.execute(bookId, updatedDto);

        // Assert
        assertNotNull(result);
        assertEquals(existingTitle, result.getTitle());
        assertEquals(newImageUrl, result.getImageUrl());

        verify(bookRepository).findById(id);
        verify(bookRepository, never()).existsByTitleIgnoreCase(anyString());
        verify(cloudinaryService).deleteImage(existingImageUrl);
        verify(cloudinaryService).uploadImage(any(MockMultipartFile.class));
        verify(bookRepository).save(any(BookEntity.class));
    }

    @Test
    void shouldUpdateBookWithoutImageChange() {
        // Arrange
        UUID id = UUID.randomUUID();
        BookId bookId = BookId.of(id);

        String oldTitle = "Old Title";
        String newTitle = "New Title";
        String imageUrl = "http://example.com/existing_image.png";

        BookEntity existingEntity = BookMapper.toEntity(sampleBook(bookId, oldTitle));
        existingEntity.setImageUrl(imageUrl);

        // DTO sin archivo de imagen, solo actualización de título
        BookDTO updatedDto = sampleBookDTO(id, newTitle, null);
        updatedDto.setImage(null);

        // Mocks
        when(bookRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(bookRepository.existsByTitleIgnoreCase(newTitle)).thenReturn(false);

        Book updatedDomain = Book.update(
                BookMapper.toDomain(existingEntity),
                BookMapper.toDomain(sampleBookDTO(id, newTitle, imageUrl))
        );
        BookEntity savedEntity = BookMapper.toEntity(updatedDomain);
        when(bookRepository.save(any(BookEntity.class))).thenReturn(savedEntity);

        // Act
        BookDTO result = updateBookUseCase.execute(bookId, updatedDto);

        // Assert
        assertNotNull(result);
        assertEquals(newTitle, result.getTitle());
        assertEquals(imageUrl, result.getImageUrl());

        verify(bookRepository).findById(id);
        verify(bookRepository).existsByTitleIgnoreCase(newTitle);
        verify(cloudinaryService, never()).deleteImage(anyString());
        verify(cloudinaryService, never()).uploadImage(any());
        verify(bookRepository).save(any(BookEntity.class));
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