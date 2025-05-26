package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.UserDTO;
import com.adi.gab.application.dto.request.UpdateEmailRequest;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.domain.types.UserRole;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import com.adi.gab.infrastructure.security.AuthenticatedUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    private UpdateUserUseCase updateUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(cacheManager.getCache("recoverCodes")).thenReturn(cache);
        updateUserUseCase = new UpdateUserUseCase(userRepository, authenticatedUserProvider, cacheManager);
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        UUID userId = UUID.randomUUID();
        UserEntity oldEntity = UserEntity.builder()
                .id(userId)
                .firstName("John")
                .lastNames("Doe")
                .email("john@example.com")
                .password("123456")
                .role(UserRole.CLIENT)
                .favorites(new HashSet<>())
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(userId)
                .firstName("Johnny")
                .lastName("Doe")
                .email("johnny@example.com")
                .password("newpass")
                .role(UserRole.CLIENT)
                .favorites(List.of())
                .build();

        when(authenticatedUserProvider.getAuthenticatedUserId()).thenReturn(new UserId(userId));
        when(userRepository.findById(userId)).thenReturn(Optional.of(oldEntity));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDTO updatedDTO = updateUserUseCase.execute(userDTO);

        assertEquals("Johnny", updatedDTO.getFirstName());
        assertEquals("Doe", updatedDTO.getLastName());
        assertEquals("johnny@example.com", updatedDTO.getEmail());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();
        UserDTO userDTO = UserDTO.builder()
                .id(userId)
                .firstName("Johnny")
                .lastName("Doe")
                .email("johnny@example.com")
                .password("newpass")
                .role(UserRole.CLIENT)
                .favorites(List.of())
                .build();

        when(authenticatedUserProvider.getAuthenticatedUserId()).thenReturn(new UserId(userId));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> updateUserUseCase.execute(userDTO));
    }

    @Test
    void shouldUpdateEmailSuccessfully() {
        String oldEmail = "old@example.com";
        String newEmail = "new@example.com";
        String code = "123456";
        UUID userId = UUID.randomUUID();

        UserEntity user = UserEntity.builder()
                .id(userId)
                .firstName("John")
                .lastNames("Doe")
                .email(oldEmail)
                .password("pass")
                .role(UserRole.CLIENT)
                .favorites(new HashSet<>())
                .build();

        UpdateEmailRequest request = mock(UpdateEmailRequest.class);
        when(request.getOldEmail()).thenReturn(oldEmail);
        when(request.getNewEmail()).thenReturn(newEmail);
        when(request.getRecoveryCode()).thenReturn(code);

        when(userRepository.existsByEmail(oldEmail)).thenReturn(true);
        when(userRepository.existsByEmail(newEmail)).thenReturn(false);
        when(authenticatedUserProvider.getAuthenticatedUserId()).thenReturn(new UserId(userId));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cache.get(oldEmail, String.class)).thenReturn(code);

        updateUserUseCase.updateEmail(request);

        verify(userRepository).save(user);
        verify(cache).evict(oldEmail);
        assertEquals(newEmail, user.getEmail());
    }

    @Test
    void shouldThrowWhenOldEmailDoesNotExist() {
        UpdateEmailRequest request = mock(UpdateEmailRequest.class);
        when(request.getOldEmail()).thenReturn("old@example.com");

        when(userRepository.existsByEmail("old@example.com")).thenReturn(false);

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            updateUserUseCase.updateEmail(request);
        });

        assertTrue(exception.getMessage().contains("No user found with email"));
    }

    @Test
    void shouldThrowWhenRecoveryCodeIsInvalid() {
        UpdateEmailRequest request = mock(UpdateEmailRequest.class);
        when(request.getOldEmail()).thenReturn("old@example.com");
        when(request.getRecoveryCode()).thenReturn("wrongcode");

        when(userRepository.existsByEmail("old@example.com")).thenReturn(true);
        when(cache.get("old@example.com", String.class)).thenReturn("correctcode");

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            updateUserUseCase.updateEmail(request);
        });

        assertTrue(exception.getMessage().contains("Invalid or expired recovery code"));
    }

    @Test
    void shouldThrowWhenNewEmailAlreadyExists() {
        UpdateEmailRequest request = mock(UpdateEmailRequest.class);
        when(request.getOldEmail()).thenReturn("old@example.com");
        when(request.getNewEmail()).thenReturn("new@example.com");
        when(request.getRecoveryCode()).thenReturn("123456");

        when(userRepository.existsByEmail("old@example.com")).thenReturn(true);
        when(cache.get("old@example.com", String.class)).thenReturn("123456");
        when(userRepository.existsByEmail("new@example.com")).thenReturn(true);

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            updateUserUseCase.updateEmail(request);
        });

        assertTrue(exception.getMessage().contains("Email already in use"));
    }

    @Test
    void shouldThrowWhenUserNotFoundDuringEmailUpdate() {
        UUID userId = UUID.randomUUID();

        UpdateEmailRequest request = mock(UpdateEmailRequest.class);
        when(request.getOldEmail()).thenReturn("old@example.com");
        when(request.getNewEmail()).thenReturn("new@example.com");
        when(request.getRecoveryCode()).thenReturn("123456");

        when(userRepository.existsByEmail("old@example.com")).thenReturn(true);
        when(cache.get("old@example.com", String.class)).thenReturn("123456");
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(authenticatedUserProvider.getAuthenticatedUserId()).thenReturn(new UserId(userId));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            updateUserUseCase.updateEmail(request);
        });

        assertTrue(exception.getMessage().contains("User not found"));
    }
}
