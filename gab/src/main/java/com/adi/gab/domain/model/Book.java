package com.adi.gab.domain.model;

import com.adi.gab.domain.exception.BookExceptions;
import com.adi.gab.domain.valueobject.BookId;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Book {
    private BookId id;
    private String title;
    private String publisher;
    private String description;
    private Integer year;
    private Integer quantity;
    private Double price;
    private Double qualification;
    private String categories;

    public static Book create(Book book) {
        if (book.getId().value() == null) throw new BookExceptions.NullBookArgumentException("Book Id");
        if (book.getTitle().isEmpty()) throw new BookExceptions.NullBookArgumentException("Book Title");
        if (book.getPublisher().isEmpty()) throw new BookExceptions.NullBookArgumentException("Book Publisher");
        if (book.getDescription().isEmpty()) throw new BookExceptions.NullBookArgumentException("Book Description");
        if (book.getYear() <= 0) throw new BookExceptions.NullBookArgumentException("Book Year");
        if (book.getPrice() <= 0) throw new BookExceptions.NullBookArgumentException("Book Price");

        return Book
                .builder()
                .id(book.getId())
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

    public static Book update(Book oldBook, Book newBook) {
        return Book.builder()
                .id(oldBook.getId())
                .title(
                        (newBook.getTitle() != null && !newBook.getTitle().isEmpty() &&
                                !newBook.getTitle().equals(oldBook.getTitle()))
                                ? newBook.getTitle()
                                : oldBook.getTitle()
                )
                .publisher(
                        (newBook.getPublisher() != null && !newBook.getPublisher().isEmpty() &&
                                !newBook.getPublisher().equals(oldBook.getPublisher()))
                                ? newBook.getPublisher()
                                : oldBook.getPublisher()
                )
                .description(
                        (newBook.getDescription() != null && !newBook.getDescription().isEmpty() &&
                                !newBook.getDescription().equals(oldBook.getDescription()))
                                ? newBook.getDescription()
                                : oldBook.getDescription()
                )
                .year(
                        (newBook.getYear() != null && !newBook.getYear().equals(oldBook.getYear()))
                                ? newBook.getYear()
                                : oldBook.getYear()
                )
                .price(
                        (newBook.getPrice() != null && !newBook.getPrice().equals(oldBook.getPrice()))
                                ? newBook.getPrice()
                                : oldBook.getPrice()
                )
                .qualification(
                        (newBook.getQualification() != null && !newBook.getQualification().equals(oldBook.getQualification()))
                                ? newBook.getQualification()
                                : oldBook.getQualification()
                )
                .categories(
                        (newBook.getCategories() != null && !newBook.getCategories().isEmpty() &&
                                !newBook.getCategories().equals(oldBook.getCategories()))
                                ? newBook.getCategories()
                                : oldBook.getCategories()
                )
                .build();
    }
}
