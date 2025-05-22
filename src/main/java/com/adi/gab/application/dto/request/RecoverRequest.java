package com.adi.gab.application.dto.request;

import lombok.Getter;

@Getter
public class RecoverRequest {
    private String email;
    private String recoverCode;
    private String newPassword;
}
