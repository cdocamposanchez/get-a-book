package com.adi.gab.application.mapper;

import com.adi.gab.domain.model.Book;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;

public class BookMapper {

    private BookMapper() {}

    public static BookEntity toEntity(Book book) {
        return BookEntity.builder()
                .id(book.getId().value())
                .title(book.getTitle())
                .publisher(book.getPublisher())
                .description(book.getDescription())
                .imageUrl(book.getImageUrl())
                .year(book.getYear())
                .quantity(book.getQuantity())
                .price(book.getPrice())
                .qualification(book.getQualification())
                .categories(book.getCategories())
                .build();
    }

    public static Book toDomain(BookEntity entity) {
        return Book.builder()
                .id(new BookId(entity.getId()))
                .title(entity.getTitle())
                .publisher(entity.getPublisher())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .year(entity.getYear())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .qualification(entity.getQualification())
                .categories(entity.getCategories())
                .build();
    }

    public static Book toDomain(BookDTO dto) {
        return Book.builder()
                .id(BookId.of(dto.getId()))
                .title(dto.getTitle())
                .publisher(dto.getPublisher())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .year(dto.getYear())
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .qualification(dto.getQualification())
                .categories(dto.getCategories())
                .build();
    }

    public static BookDTO toDto(Book book) {
        return BookDTO
                .builder()
                .id(book.getId().value())
                .title(book.getTitle())
                .publisher(book.getPublisher())
                .description(book.getDescription())
                .imageUrl(book.getImageUrl())
                .year(book.getYear())
                .quantity(book.getQuantity())
                .price(book.getPrice())
                .qualification(book.getQualification())
                .categories(book.getCategories())
                .build();
    }

    public static BookDTO toDto(BookEntity book) {
        return BookDTO
                .builder()
                .id(book.getId())
                .title(book.getTitle())
                .publisher(book.getPublisher())
                .description(book.getDescription())
                .imageUrl(book.getImageUrl())
                .year(book.getYear())
                .quantity(book.getQuantity())
                .price(book.getPrice())
                .qualification(book.getQualification())
                .categories(book.getCategories())
                .build();
    }
}
