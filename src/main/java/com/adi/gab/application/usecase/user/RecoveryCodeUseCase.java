package com.adi.gab.application.usecase.user;

import com.adi.gab.application.exception.ApplicationException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecoveryCodeUseCase {

    private final JavaMailSender mailSender;
    private final Cache cache;

    public RecoveryCodeUseCase(JavaMailSender mailSender, CacheManager cacheManager) {
        this.mailSender = mailSender;
        this.cache = cacheManager.getCache("recoverCodes");
    }


    public void sendRecoveryCode(String email) {
        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        cache.put(email, code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("GET A BOOK Recovery Code");
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
}
