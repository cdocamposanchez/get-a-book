package com.adi.gab.infrastructure.mapper;

import com.adi.gab.domain.model.Book;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.dto.BookDTO;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;

import java.util.List;

public class BookMapper {

    private BookMapper() {}

    public static BookEntity toEntity(Book book) {
        return BookEntity.builder()
                .id(book.getId().value())
                .title(book.getTitle())
                .publisher(book.getPublisher())
                .description(book.getDescription())
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
                .year(book.getYear())
                .quantity(book.getQuantity())
                .price(book.getPrice())
                .qualification(book.getQualification())
                .categories(book.getCategories())
                .build();
    }

    public static List<Book> toDomainList(List<BookEntity> entities) {
        return entities.stream()
                .map(BookMapper::toDomain)
                .toList();
    }

    public static List<BookEntity> toEntityList(List<Book> books) {
        return books.stream()
                .map(BookMapper::toEntity)
                .toList();
    }
}
