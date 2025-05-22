package com.adi.gab.application.usecase.user;

import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class ValidateTokenUseCase {

    private final JwtTokenProvider tokenProvider;

    public ValidateTokenUseCase(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public boolean isValid(String token) {
        return tokenProvider.isValid(token);
    }

    public UserId getUserId(String token) {
        if (!tokenProvider.isValid(token)) {
            throw new IllegalArgumentException("Token inválido o expirado");
        }
        return tokenProvider.extractUserId(token);
    }
}