package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.request.RecoverRequest;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@Service
public class RecoverPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Cache cache;

    public RecoverPasswordUseCase(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cache = cacheManager.getCache("recoverCodes");
    }

    public void changePassword(RecoverRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApplicationException("No user found with email: " + request.getEmail(), this.getClass().getSimpleName()));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        cache.evict(request.getEmail());
    }
}
