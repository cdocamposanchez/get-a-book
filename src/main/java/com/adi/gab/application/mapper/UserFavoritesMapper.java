package com.adi.gab.application.mapper;

import com.adi.gab.domain.valueobject.BookId;

import java.util.List;
import java.util.UUID;

public class UserFavoritesMapper {

    public static List<BookId> toDomain(List<UUID> entity) {
        return entity.stream()
                .map(BookId::of)
                .toList();
    }

    public static List<UUID> toEntity(List<BookId> bookIds) {
        return bookIds.stream()
                .map(BookId::value)
                .toList();
    }

    private UserFavoritesMapper() {}
}
