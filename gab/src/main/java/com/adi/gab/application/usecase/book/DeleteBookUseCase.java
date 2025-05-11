package com.adi.gab.application.usecase.book;


import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteBookUseCase {
    private final BookRepository bookRepository;

    public DeleteBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void execute(BookId bookId) {
        if (!bookRepository.existsById(bookId.value())) {
            throw new ApplicationException("Book not found with ID: " + bookId.value(), this.getClass().getSimpleName());
        }

        bookRepository.deleteById(bookId.value());
    }
}
