package com.adi.gab.infrastructure.smtp;

public interface EmailService {
    void send(String to, String subject, String body);
}


