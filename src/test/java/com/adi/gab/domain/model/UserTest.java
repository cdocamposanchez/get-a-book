package com.adi.gab.domain.model;

import com.adi.gab.domain.exception.UserExceptions;
import com.adi.gab.domain.types.UserRole;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private UserId validId;
    private User baseUser;

    @BeforeEach
    void setUp() {
        validId = UserId.of(UUID.randomUUID());

        baseUser = User.builder()
                .id(validId)
                .firstName("Alice")
                .lastNames("Doe Smith")
                .email("alice@example.com")
                .password("securePass123")
                .role(UserRole.USER)
                .favorites(List.of(BookId.of(UUID.randomUUID())))
                .build();
    }

    // ---------- TESTS create() ----------

    @Test
    void shouldCreateUserSuccessfully() {
        User created = User.create("Bob", "Johnson", "bob@example.com", "password123", UserRole.ADMIN);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Bob", created.getFirstName());
        assertEquals("Johnson", created.getLastNames());
        assertEquals("bob@example.com", created.getEmail());
        assertEquals("password123", created.getPassword());
        assertEquals(UserRole.ADMIN, created.getRole());
        assertNotNull(created.getFavorites());
        assertTrue(created.getFavorites().isEmpty());
    }

    @Test
    void shouldThrowWhenFirstNameIsNull() {
        Exception ex = assertThrows(UserExceptions.NullUserArgumentException.class, () ->
                User.create(null, "Last", "email@example.com", "pass", UserRole.USER));
        assertEquals("Argument: FirstName Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenFirstNameIsEmpty() {
        Exception ex = assertThrows(UserExceptions.NullUserArgumentException.class, () ->
                User.create("", "Last", "email@example.com", "pass", UserRole.USER));
        assertEquals("Argument: FirstName Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenLastNameIsNull() {
        Exception ex = assertThrows(UserExceptions.NullUserArgumentException.class, () ->
                User.create("First", null, "email@example.com", "pass", UserRole.USER));
        assertEquals("Argument: LastName Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenLastNameIsEmpty() {
        Exception ex = assertThrows(UserExceptions.NullUserArgumentException.class, () ->
                User.create("First", "", "email@example.com", "pass", UserRole.USER));
        assertEquals("Argument: LastName Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenEmailIsNull() {
        Exception ex = assertThrows(UserExceptions.NullUserArgumentException.class, () ->
                User.create("First", "Last", null, "pass", UserRole.USER));
        assertEquals("Argument: Email Cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowWhenEmailIsEmpty() {
        Exception ex = assertThrows(UserExceptions.NullUserArgumentException.class, () ->
                User.create("First", "Last", "", "pass", UserRole.USER));
        assertEquals("Argument: Email Cannot be null.", ex.getMessage());
    }

    // ---------- TESTS update() ----------

    @Test
    void shouldUpdateUserWithNewValues() {
        List<BookId> newFavorites = List.of(BookId.of(UUID.randomUUID()));

        User newUser = User.builder()
                .firstName("Alicia")
                .lastNames("Doe Johnson")
                .email("newalice@example.com")
                .password("newPassword!")
                .favorites(newFavorites)
                .build();

        User updated = User.update(baseUser, newUser);

        assertEquals("Alicia", updated.getFirstName());
        assertEquals("Doe Johnson", updated.getLastNames());
        assertEquals("newalice@example.com", updated.getEmail());
        assertEquals("newPassword!", updated.getPassword());
        assertEquals(newFavorites, updated.getFavorites());
        assertEquals(baseUser.getId(), updated.getId());
    }

    @Test
    void shouldReturnSameValuesWhenNewUserHasNullsOrSameValues() {
        User newUser = User.builder().build(); // todos los campos nulos

        User updated = User.update(baseUser, newUser);

        assertEquals(baseUser.getFirstName(), updated.getFirstName());
        assertEquals(baseUser.getLastNames(), updated.getLastNames());
        assertEquals(baseUser.getEmail(), updated.getEmail());
        assertEquals(baseUser.getPassword(), updated.getPassword());
        assertEquals(baseUser.getFavorites(), updated.getFavorites());
        assertEquals(baseUser.getId(), updated.getId());
    }
}
