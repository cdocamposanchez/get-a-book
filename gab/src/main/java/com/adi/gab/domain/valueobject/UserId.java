package com.adi.gab.domain.valueobject;

import com.adi.gab.domain.exception.UserExceptions;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        if (value == null) throw new UserExceptions.NullUserArgumentException("UserId");
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId of(UUID value){
        return new UserId(value);
    }
}
