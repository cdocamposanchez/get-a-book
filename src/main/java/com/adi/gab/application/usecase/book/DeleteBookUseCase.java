package com.adi.gab.application.usecase.book;


import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.config.cloudinary.CloudinaryService;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteBookUseCase {
    private final BookRepository bookRepository;
    private final CloudinaryService cloudinaryService;

    public DeleteBookUseCase(BookRepository bookRepository, CloudinaryService cloudinaryService) {
        this.bookRepository = bookRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public void execute(BookId bookId) {
        Optional<BookEntity> book = bookRepository.findById(bookId.value());
        if (book.isEmpty()) {
            throw new ApplicationException("Book not found with ID: " + bookId.value(), this.getClass().getSimpleName());
        }

        cloudinaryService.deleteImage(book.get().getImageUrl());
        bookRepository.deleteById(bookId.value());
    }
}
