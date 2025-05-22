package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.AuthDTO;
import com.adi.gab.application.dto.request.LoginRequest;
import com.adi.gab.domain.types.UserRole;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.security.CustomUserDetails;
import com.adi.gab.infrastructure.security.JwtTokenProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginUseCaseTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Mock
    private Authentication authentication;

    private final String email = "user@gmail.com";
    private final String password = "securePassword";
    private final UUID userId = UUID.randomUUID();
    private final String token = "jwt-token";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private LoginRequest buildLoginRequest(String email, String password) throws Exception {
        LoginRequest request = new LoginRequest();

        Field emailField = LoginRequest.class.getDeclaredField("email");
        Field passwordField = LoginRequest.class.getDeclaredField("password");
        emailField.setAccessible(true);
        passwordField.setAccessible(true);

        emailField.set(request, email);
        passwordField.set(request, password);

        return request;
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        // Arrange
        LoginRequest request = buildLoginRequest(email, password);

        UserEntity user = UserEntity.builder()
                .id(userId)
                .email(email)
                .password(password)
                .role(UserRole.CLIENT)
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(tokenProvider.generateToken(userDetails)).thenReturn(token);

        // Act
        AuthDTO result = loginUseCase.execute(request);

        // Assert
        assertNotNull(result);
        assertEquals(token, result.getToken());
        assertEquals(UserRole.CLIENT, result.getUserRole());
        assertEquals(userId, result.getUserId());

        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider).generateToken(userDetails);
    }

    @Test
    void shouldThrowExceptionWhenAuthenticationFails() throws Exception {
        // Arrange
        LoginRequest request = buildLoginRequest(email, password);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            loginUseCase.execute(request);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider, never()).generateToken(any());
    }
}
