package com.adi.gab.application.usertest;

import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.usecase.user.UpdateFavoriteUseCase;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import com.adi.gab.infrastructure.security.AuthenticatedUserProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateFavoriteUseCaseTest {

        @Mock
        private UserRepository userRepository;

        @Mock
        private AuthenticatedUserProvider authenticatedUserProvider;

        @InjectMocks
        private UpdateFavoriteUseCase updateFavoriteUseCase;

        private UUID userId;
        private UUID bookId;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            userId = UUID.randomUUID();
            bookId = UUID.randomUUID();
            when(authenticatedUserProvider.getAuthenticatedUserId()).thenReturn(new UserId(userId));
        }

        @Test
        void shouldAddBookToFavorites() {
            // Arrange
            UserEntity user = UserEntity.builder()
                    .id(userId)
                    .favorites(new ArrayList<>())
                    .build();

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(userRepository.save(any(UserEntity.class))).thenReturn(user);

            // Act
            String result = updateFavoriteUseCase.execute(BookId.of(bookId));

            // Assert
            assertEquals("Added", result);
            assertTrue(user.getFavorites().contains(bookId));
            verify(userRepository).save(user);
        }

        @Test
        void shouldRemoveBookFromFavorites() {
            // Arrange
            UserEntity user = UserEntity.builder()
                    .id(userId)
                    .favorites(new ArrayList<>(Collections.singletonList(bookId))) // Ya contiene el libro
                    .build();

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(userRepository.save(any(UserEntity.class))).thenReturn(user);

            // Act
            String result = updateFavoriteUseCase.execute(BookId.of(bookId));

            // Assert
            assertEquals("Removed", result);
            assertFalse(user.getFavorites().contains(bookId));
            verify(userRepository).save(user);
        }

        @Test
        void shouldThrowExceptionIfUserNotFound() {
            // Arrange
            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            // Act & Assert
            ApplicationException exception = assertThrows(ApplicationException.class, () -> {
                updateFavoriteUseCase.execute(BookId.of(bookId));
            });

            assertTrue(exception.getMessage().contains("User with ID: " + userId + " not found"));
            verify(userRepository, never()).save(any());
        }

        @Test
        void shouldThrowExceptionIfSaveFails() {
            // Arrange
            UserEntity user = UserEntity.builder()
                    .id(userId)
                    .favorites(new ArrayList<>())
                    .build();

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("DB error"));

            // Act & Assert
            ApplicationException exception = assertThrows(ApplicationException.class, () -> {
                updateFavoriteUseCase.execute(BookId.of(bookId));
            });

            assertTrue(exception.getMessage().contains("Error during saving: DB error"));
        }
    }
