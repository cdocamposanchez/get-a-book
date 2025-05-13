package com.adi.gab.domain.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.adi.gab.domain.exception.BookExceptions;
import com.adi.gab.domain.valueobject.BookId;
import java.math.BigDecimal;
import java.time.Year;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {

    private BookId validId;
    private Book baseBook;

    @BeforeEach
    void setUp() {
        validId = BookId.of(UUID.randomUUID());

        baseBook = Book.builder()
                .id(validId)
                .title("Effective Java")
                .publisher("Addison-Wesley")
                .description("A guide to Java best practices")
                .imageUrl("https://example.com/image.jpg")
                .year(2020)
                .quantity(5)
                .price(BigDecimal.valueOf(39.99))
                .qualification(4.5)
                .categories("Programming")
                .build();
    }

    // ---------- TESTS create() ----------

    @Test
    void shouldCreateBookSuccessfully() {
        Book created = Book.create(baseBook);
        assertNotNull(created);
        assertEquals("Effective Java", created.getTitle());
    }

    @Test
    void shouldThrowWhenTitleIsEmpty() {
        Book invalid = Book.builder()
                .id(validId)
                .title("")
                .publisher("Publisher")
                .description("Description")
                .year(2020)
                .price(BigDecimal.valueOf(10))
                .build();

        Exception ex = assertThrows(BookExceptions.NullBookArgumentException.class, () -> Book.create(invalid));
        assertEquals("Argument: Book Title Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenPublisherIsEmpty() {
        Book invalid = Book.builder()
                .id(validId)
                .title("Title")
                .publisher("")
                .description("Description")
                .year(2020)
                .price(BigDecimal.valueOf(10))
                .build();

        Exception ex = assertThrows(BookExceptions.NullBookArgumentException.class, () -> Book.create(invalid));
        assertEquals(	"Argument: Book Publisher Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenDescriptionIsEmpty() {
        Book invalid = Book.builder()
                .id(validId)
                .title("Title")
                .publisher("Publisher")
                .description("")
                .year(2020)
                .price(BigDecimal.valueOf(10))
                .build();

        Exception ex = assertThrows(BookExceptions.NullBookArgumentException.class, () -> Book.create(invalid));
        assertEquals("Argument: Book Description Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenYearIsInvalid() {
        Book invalid = Book.builder()
                .id(validId)
                .title("Title")
                .publisher("Publisher")
                .description("Description")
                .year(0)
                .price(BigDecimal.valueOf(10))
                .build();

        Exception ex = assertThrows(BookExceptions.NullBookArgumentException.class, () -> Book.create(invalid));
        assertEquals(	"Argument: Book Year Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenPriceIsZero() {
        Book invalid = Book.builder()
                .id(validId)
                .title("Title")
                .publisher("Publisher")
                .description("Description")
                .year(2020)
                .price(BigDecimal.ZERO)
                .build();

        Exception ex = assertThrows(BookExceptions.NullBookArgumentException.class, () -> Book.create(invalid));
        assertEquals("Argument: Book Price Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenYearIsInFuture() {
        int futureYear = Year.now().getValue() + 1;

        Book invalid = Book.builder()
                .id(validId)
                .title("Title")
                .publisher("Publisher")
                .description("Description")
                .year(futureYear)
                .price(BigDecimal.valueOf(20))
                .build();

        Exception ex = assertThrows(BookExceptions.LogicException.class, () -> Book.create(invalid));
        assertEquals("Business exception: The year of the book cannot be greater than the actual year",
                ex.getMessage());
    }

    // ---------- TESTS update() ----------

    @Test
    void shouldUpdateBookWithNewValues() {
        Book newBook = Book.builder()
                .id(validId) // se ignora
                .title("Clean Code")
                .publisher("Prentice Hall")
                .description("A new description")
                .imageUrl("https://new.url/image.png")
                .year(2021)
                .quantity(5) // también se ignora
                .price(BigDecimal.valueOf(49.99))
                .qualification(5.0)
                .categories("Software Engineering")
                .build();

        Book updated = Book.update(baseBook, newBook);

        assertEquals("Clean Code", updated.getTitle());
        assertEquals("Prentice Hall", updated.getPublisher());
        assertEquals("A new description", updated.getDescription());
        assertEquals("https://new.url/image.png", updated.getImageUrl());
        assertEquals(2021, updated.getYear());
        assertEquals(BigDecimal.valueOf(49.99), updated.getPrice());
        assertEquals(5.0, updated.getQualification());
        assertEquals("Software Engineering", updated.getCategories());

        // valores que no cambian
        assertEquals(validId, updated.getId());
    }

    @Test
    void shouldReturnSameValuesWhenNewBookHasNullsOrSameValues() {
        Book newBook = Book.builder().build(); // valores nulos

        Book updated = Book.update(baseBook, newBook);

        assertEquals(baseBook.getTitle(), updated.getTitle());
        assertEquals(baseBook.getPublisher(), updated.getPublisher());
        assertEquals(baseBook.getDescription(), updated.getDescription());
        assertEquals(baseBook.getImageUrl(), updated.getImageUrl());
        assertEquals(baseBook.getYear(), updated.getYear());
        assertEquals(baseBook.getPrice(), updated.getPrice());
        assertEquals(baseBook.getQualification(), updated.getQualification());
        assertEquals(baseBook.getCategories(), updated.getCategories());
    }
}