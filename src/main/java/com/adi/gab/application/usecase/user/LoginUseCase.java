package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.request.LoginRequest;
import com.adi.gab.application.dto.TokenDTO;
import com.adi.gab.infrastructure.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    public LoginUseCase(AuthenticationManager authManager, JwtTokenProvider tokenProvider) {
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
    }

    public TokenDTO execute(LoginRequest request) {
        var auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var userDetails = (UserDetails) auth.getPrincipal();
        String token = tokenProvider.generateToken(userDetails);
        return TokenDTO.builder().token(token).build();
    }
}
