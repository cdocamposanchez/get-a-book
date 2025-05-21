package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.AuthDTO;
import com.adi.gab.application.dto.request.RegisterRequest;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.usecase.user.RegisterUseCase;
import com.adi.gab.domain.types.UserRole;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import com.adi.gab.infrastructure.security.CustomUserDetails;
import com.adi.gab.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterUseCaseTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private RegisterUseCase registerUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        tokenProvider = mock(JwtTokenProvider.class);
        registerUseCase = new RegisterUseCase(userRepository, passwordEncoder, tokenProvider);
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        // Arrange
        RegisterRequest request = createRegisterRequest("John", "Doe", "john@example.com", "password123");

        when(userRepository.existsByEmail("andrea@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("password1927")).thenReturn("encodedPassword");

        UserEntity savedEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .firstName("Andrea")
                .lastNames("Gonzales")
                .email("andrea@gmail.com")
                .password("encodedPassword")
                .role(UserRole.CLIENT)
                .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedEntity);
        when(tokenProvider.generateToken(any(CustomUserDetails.class))).thenReturn("mockedToken");

        // Act
        AuthDTO result = registerUseCase.execute(request);

        // Assert
        assertNotNull(result);
        assertEquals("mockedToken", result.getToken());
        assertEquals(UserRole.CLIENT, result.getUserRole());
        assertEquals(savedEntity.getId(), result.getUserId());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() throws Exception {
        // Arrange
        RegisterRequest request = createRegisterRequest("Jane", "Smith", "jane@gmail.com", "securePass");
        when(userRepository.existsByEmail("jane@gmail.com")).thenReturn(true);

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> registerUseCase.execute(request));

        assertTrue(exception.getMessage().contains("There is already a user registered with the email: jane@gmail.com"));
        verify(userRepository, never()).save(any());
    }

    // Utilidad para setear campos privados por reflexión
    private RegisterRequest createRegisterRequest(String firstName, String lastName, String email, String password) throws Exception {
        RegisterRequest request = new RegisterRequest();
        setField(request, "firstName", firstName);
        setField(request, "lastName", lastName);
        setField(request, "email", email);
        setField(request, "password", password);
        return request;
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}

