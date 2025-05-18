package com.adi.gab.domain.types;

public enum UserRole {
    CLIENT,
    ADMIN;

    public static UserRole fromStringIgnoreCase(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No such a UserRole registered with value: " + value);
    }
}
