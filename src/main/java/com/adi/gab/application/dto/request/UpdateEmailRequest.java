package com.adi.gab.application.dto.request;

import lombok.Getter;

@Getter
public class UpdateEmailRequest {
    private String oldEmail;
    private String newEmail;
    private String recoveryCode;
}
