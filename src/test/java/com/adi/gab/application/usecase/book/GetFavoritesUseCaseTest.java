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

import java.util.*;

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
                .price(new java.math.BigDecimal("19.99"))
                .year(2023)
                .imageUrl("image.jpg")
                .quantity(10)
                .qualification(4.5)
                .categories("Fiction")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(userIdValue)
                .favorites(List.of(favoriteBookId))
                .build();

        when(authenticatedUserProvider.getAuthenticatedUserId()).thenReturn(userId);
        when(userRepository.findById(userIdValue)).thenReturn(Optional.of(userEntity));
        when(bookRepository.findById(favoriteBookId)).thenReturn(Optional.of(bookEntity));

        // Act
        List<BookDTO> result = getFavoritesUseCase.execute();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Sample Book", result.getFirst().getTitle());

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
    }

    @Test
    void shouldThrowExceptionWhenBookInFavoritesNotFound() {
        // Arrange
        UUID userIdValue = UUID.randomUUID();
        UserId userId = new UserId(userIdValue);
        UUID missingBookId = UUID.randomUUID();

        UserEntity userEntity = UserEntity.builder()
                .id(userIdValue)
                .favorites(List.of(missingBookId))
                .build();

        when(authenticatedUserProvider.getAuthenticatedUserId()).thenReturn(userId);
        when(userRepository.findById(userIdValue)).thenReturn(Optional.of(userEntity));
        when(bookRepository.findById(missingBookId)).thenReturn(Optional.empty());

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            getFavoritesUseCase.execute();
        });

        assertTrue(exception.getMessage().contains("Book with ID: " + missingBookId + " not found"));
    }
}
