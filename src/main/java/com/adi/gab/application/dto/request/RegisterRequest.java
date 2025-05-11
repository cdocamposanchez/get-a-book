package com.adi.gab.application.dto.request;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
