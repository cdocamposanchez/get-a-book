package com.adi.gab.domain.types;

public enum SortOrder {
    ASC, DESC;

    public static SortOrder fromString(String value) {
        if (value == null) return ASC;
        try {
            return SortOrder.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException _) {
            return ASC;
        }
    }
}