package com.adi.gab.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AuthDTO {
    private String token;
    private UUID userId;
}
