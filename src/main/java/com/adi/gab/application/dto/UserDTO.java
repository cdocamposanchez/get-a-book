package com.adi.gab.application.dto;

import com.adi.gab.domain.types.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@Setter
public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastNames;
    private String email;
    private String password;
    private UserRole role;
    private List<UUID> favorites;
}
