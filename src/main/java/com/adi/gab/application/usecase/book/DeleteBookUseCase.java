package com.adi.gab.application.usecase.book;


import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.config.cloudinary.CloudinaryService;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeleteBookUseCase {
    private final BookRepository bookRepository;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;

    public DeleteBookUseCase(BookRepository bookRepository, CloudinaryService cloudinaryService, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.cloudinaryService = cloudinaryService;
        this.userRepository = userRepository;
    }

    public void execute(BookId bookId) {
        Optional<BookEntity> book = bookRepository.findById(bookId.value());
        if (book.isEmpty()) {
            throw new ApplicationException("Book not found with ID: " + bookId.value(), this.getClass().getSimpleName());
        }

        List<UserEntity> usersWithFavorite = userRepository.findAllByFavoritesContaining(bookId.value());

        usersWithFavorite.forEach(user -> user.getFavorites().remove(bookId.value()));

        userRepository.saveAll(usersWithFavorite);

        cloudinaryService.deleteImage(book.get().getImageUrl());
        bookRepository.deleteById(bookId.value());
    }
}
