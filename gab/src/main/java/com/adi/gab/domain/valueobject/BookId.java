package com.adi.gab.domain.valueobject;

import com.adi.gab.domain.exception.BookExceptions;

import java.util.UUID;

public record BookId(UUID value) {
    public BookId {
        if (value == null) throw new BookExceptions.NullBookArgumentException("BookId");
    }

    public static BookId generate() {
        return new BookId(UUID.randomUUID());
    }

    public static BookId of(UUID value){
        return new BookId(value);
    }
}
