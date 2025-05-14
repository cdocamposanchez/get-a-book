package com.adi.gab.application.usertest;

import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.usecase.user.DeleteUserUseCase;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteUserUseCaseTest {

    private UserRepository userRepository;
    private DeleteUserUseCase deleteUserUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        deleteUserUseCase = new DeleteUserUseCase(userRepository);
    }

    @Test
    void shouldDeleteUserSuccessfullyWhenUserExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        UserId userId = new UserId(id);

        when(userRepository.existsById(id)).thenReturn(true);

        // Act
        deleteUserUseCase.execute(userId);

        // Assert
        verify(userRepository).existsById(id);
        verify(userRepository).deleteById(id);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();
        UserId userId = new UserId(id);

        when(userRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> deleteUserUseCase.execute(userId)
        );

        assertTrue(exception.getMessage().contains("User not found with ID: " + id));
        verify(userRepository).existsById(id);
        verify(userRepository, never()).deleteById(id);
    }
}
