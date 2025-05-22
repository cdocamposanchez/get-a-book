package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.request.RecoverRequest;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class RecoverPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final Cache cache;

    public RecoverPasswordUseCase(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  JavaMailSender mailSender,
                                  CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.cache = cacheManager.getCache("recoverCodes");
    }

    public void sendRecoveryCode(String email) {
        if (Boolean.FALSE.equals(userRepository.existsByEmail(email)))
            throw new ApplicationException("No user found with email: " + email, this.getClass().getSimpleName());

        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        cache.put(email, code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("GAB - Password Recovery Code");
        message.setText("Your recovery code is: " + code);
        mailSender.send(message);
    }

    public boolean validateRecoveryCode(String email, String code) {
        String cachedCode = cache.get(email, String.class);
        if (cachedCode == null) {
            throw new ApplicationException("Recovery code expired or invalid.", this.getClass().getSimpleName());
        }
        return cachedCode.equalsIgnoreCase(code);
    }

    public void changePassword(RecoverRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApplicationException("No user found with email: " + request.getEmail(), this.getClass().getSimpleName()));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        cache.evict(request.getEmail());
    }
}
