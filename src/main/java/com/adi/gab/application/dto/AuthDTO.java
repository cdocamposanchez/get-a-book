package com.adi.gab.application.dto;

import com.adi.gab.domain.types.UserRole;
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
    private UserRole userRole;
}
