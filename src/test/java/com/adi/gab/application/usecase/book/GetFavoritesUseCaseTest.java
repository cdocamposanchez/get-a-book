package com.adi.gab.application.usecase.book;


import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import com.adi.gab.infrastructure.security.AuthenticatedUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set; // Importar Set
import java.util.HashSet; // Importar HashSet para crear un Set mutable
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetFavoritesUseCaseTest {

    private UserRepository userRepository;
    private BookRepository bookRepository;
    private AuthenticatedUserProvider authenticatedUserProvider;
    private GetFavoritesUseCase getFavoritesUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        bookRepository = mock(BookRepository.class);
        authenticatedUserProvider = mock(AuthenticatedUserProvider.class);
        getFavoritesUseCase = new GetFavoritesUseCase(userRepository, bookRepository, authenticatedUserProvider);
    }

    @Test
    void shouldReturnFavoriteBooksSuccessfully() {
        // Arrange
        UUID userIdValue = UUID.randomUUID();
        UserId userId = new UserId(userIdValue);
        UUID favoriteBookId = UUID.randomUUID();

        BookEntity bookEntity = BookEntity.builder()
                .id(favoriteBookId)
                .title("Sample Book")
                .publisher("Test Publisher")
                .description("Test Description")
                .price(new BigDecimal("19.99"))
                .year(2023)
                .imageUrl("image.jpg")
                .quantity(10)
                .qualification(4.5)
                .categories("Fiction")
                .build();

        // **CAMBIO AQUÍ: Usar Set.of() o crear un HashSet si el builder espera un Set**
        // Si tu UserEntity.favorites es un Set, usa Set.of().
        // Si el setter de UserEntity.favorites espera un tipo mutable (ej. HashSet),
        // o si List.of() no funciona directamente para el setter,
        // puedes crear un HashSet a partir de la lista.
        UserEntity userEntity = UserEntity.builder()
                .id(userIdValue)
                .favorites(Set.of(favoriteBookId)) // Suponiendo que UserEntity.favorites es Set<UUID>
                // O si necesitas un Set mutable para el builder:
                // .favorites(new HashSet<>(List.of(favoriteBookId)))
                .build();

        when(authenticatedUserProvider.getAuthenticatedUserId()).thenReturn(userId);
        when(userRepository.findById(userIdValue)).thenReturn(Optional.of(userEntity));
        when(bookRepository.findById(favoriteBookId)).thenReturn(Optional.of(bookEntity));

        // Act
        List<BookDTO> result = getFavoritesUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample Book", result.get(0).getTitle()); // Changed to get(0) for List

        verify(authenticatedUserProvider).getAuthenticatedUserId();
        verify(userRepository).findById(userIdValue);
        verify(bookRepository).findById(favoriteBookId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        UUID userIdValue = UUID.randomUUID();
        UserId userId = new UserId(userIdValue);

        when(authenticatedUserProvider.getAuthenticatedUserId()).thenReturn(userId);
        when(userRepository.findById(userIdValue)).thenReturn(Optional.empty());

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            getFavoritesUseCase.execute();
        });

        assertTrue(exception.getMessage().contains("User with ID: " + userIdValue + " not found"));

        verify(authenticatedUserProvider).getAuthenticatedUserId();
        verify(userRepository).findById(userIdValue);
        verifyNoInteractions(bookRepository); // Added verification that bookRepository is not called
    }

    @Test
    void shouldThrowExceptionWhenBookInFavoritesNotFound() {
        // Arrange
        UUID userIdValue = UUID.randomUUID();
        UserId userId = new UserId(userIdValue);
        UUID missingBookId = UUID.randomUUID();

        // **CAMBIO AQUÍ: Usar Set.of() o crear un HashSet**
        UserEntity userEntity = UserEntity.builder()
                .id(userIdValue)
                .favorites(Set.of(missingBookId)) // Suponiendo que UserEntity.favorites es Set<UUID>
                // O si necesitas un Set mutable para el builder:
                // .favorites(new HashSet<>(List.of(missingBookId)))
                .build();

        when(authenticatedUserProvider.getAuthenticatedUserId()).thenReturn(userId);
        when(userRepository.findById(userIdValue)).thenReturn(Optional.of(userEntity));
        when(bookRepository.findById(missingBookId)).thenReturn(Optional.empty());

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            getFavoritesUseCase.execute();
        });

        assertTrue(exception.getMessage().contains("Book with ID: " + missingBookId + " not found"));

        verify(authenticatedUserProvider).getAuthenticatedUserId();
        verify(userRepository).findById(userIdValue);
        verify(bookRepository).findById(missingBookId); // Added verification
    }
}