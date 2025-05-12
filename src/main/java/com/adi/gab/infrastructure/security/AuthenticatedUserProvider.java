package com.adi.gab.infrastructure.security;

import com.adi.gab.domain.valueobject.UserId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthenticatedUserProvider {

    public UserId getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return UserId.of(UUID.fromString(userDetails.getId()));
        }

        throw new IllegalStateException("No authenticated user found");
    }
}