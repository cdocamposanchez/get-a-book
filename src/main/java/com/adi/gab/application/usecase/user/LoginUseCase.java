package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.request.LoginRequest;
import com.adi.gab.application.dto.AuthDTO;
import com.adi.gab.domain.types.UserRole;
import com.adi.gab.infrastructure.security.CustomUserDetails;
import com.adi.gab.infrastructure.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginUseCase {
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    public LoginUseCase(AuthenticationManager authManager, JwtTokenProvider tokenProvider) {
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
    }

    public AuthDTO execute(LoginRequest request) {
        var auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var userDetails = (CustomUserDetails) auth.getPrincipal();
        String token = tokenProvider.generateToken(userDetails);
        return AuthDTO.builder()
                .token(token)
                .userRole(UserRole.fromStringIgnoreCase(userDetails.getRole()))
                .userId(UUID.fromString(userDetails.getId())).build();
    }
}
