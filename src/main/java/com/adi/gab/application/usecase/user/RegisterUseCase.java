package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.AuthDTO;
import com.adi.gab.application.dto.request.RegisterRequest;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.mapper.UserMapper;
import com.adi.gab.domain.model.User;
import com.adi.gab.domain.types.UserRole;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import com.adi.gab.infrastructure.security.CustomUserDetails;
import com.adi.gab.infrastructure.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public RegisterUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public AuthDTO execute(RegisterRequest request) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) throw new ApplicationException(
                "There is already a user registered with the email: " + request.getEmail(),
                this.getClass().getSimpleName());

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = User.create(request.getFirstName(), request.getLastName(), request.getEmail(), encryptedPassword, UserRole.CLIENT);
        UserEntity userEntity = userRepository.save(UserMapper.toEntity(newUser));

        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        String token = tokenProvider.generateToken(userDetails);
        return AuthDTO.builder()
                .token(token)
                .userRole(UserRole.fromStringIgnoreCase(userDetails.getRole()))
                .userId(userEntity.getId()).build();
    }
}
