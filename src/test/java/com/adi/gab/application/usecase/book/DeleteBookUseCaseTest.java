package com.adi.gab.application.usecase.book;

import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.config.cloudinary.CloudinaryService;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteBookUseCaseTest {

    private BookRepository bookRepository;
    private CloudinaryService cloudinaryService;
    private UserRepository userRepository;
    private DeleteBookUseCase deleteBookUseCase;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        cloudinaryService = mock(CloudinaryService.class);
        userRepository = mock(UserRepository.class);
        deleteBookUseCase = new DeleteBookUseCase(bookRepository, cloudinaryService, userRepository);
    }

    private BookEntity sampleBookEntity(UUID id) {
        BookEntity book = new BookEntity();
        book.setId(id);
        book.setTitle("Sample");
        book.setDescription("A book");
        book.setPrice(BigDecimal.valueOf(9.99));
        book.setImageUrl("https://cloudinary.com/sample.jpg");
        return book;
    }

    private UserEntity sampleUserWithFavorite(UUID bookId) {
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setFavorites(new HashSet<>(Collections.singletonList(bookId)));
        return user;
    }

    @Test
    void shouldDeleteBookSuccessfully() {
        UUID bookId = UUID.randomUUID();
        BookEntity book = sampleBookEntity(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Simula que hay un usuario que tiene el libro en favoritos
        UserEntity userWithFavorite = sampleUserWithFavorite(bookId);
        List<UserEntity> usersWithFavorite = Collections.singletonList(userWithFavorite);
        when(userRepository.findAllByFavoritesContaining(bookId)).thenReturn(usersWithFavorite);

        deleteBookUseCase.execute(new BookId(bookId));

        // Verifica que la imagen fue eliminada en cloudinary
        verify(cloudinaryService).deleteImage("https://cloudinary.com/sample.jpg");

        // Verifica que el libro fue eliminado del repositorio
        verify(bookRepository).deleteById(bookId);

        // Verifica que el libro fue removido de los favoritos del usuario
        assertFalse(userWithFavorite.getFavorites().contains(bookId));

        // Verifica que se guardaron los usuarios actualizados
        verify(userRepository).saveAll(usersWithFavorite);
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            deleteBookUseCase.execute(new BookId(bookId));
        });

        assertTrue(exception.getMessage().contains("Book not found with ID:"));
        verify(cloudinaryService, never()).deleteImage(any());
        verify(bookRepository, never()).deleteById(any());
        verify(userRepository, never()).findAllByFavoritesContaining(any());
        verify(userRepository, never()).saveAll(any());
    }
}
