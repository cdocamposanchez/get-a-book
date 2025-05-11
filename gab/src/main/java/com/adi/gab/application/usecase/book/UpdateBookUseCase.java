package com.adi.gab.application.usecase.book;

import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.BookMapper;
import com.adi.gab.domain.model.Book;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateBookUseCase {
    private final BookRepository bookRepository;

    public UpdateBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO execute(BookId bookId, BookDTO bookDto) {

        BookEntity existingEntity = bookRepository.findById(bookId.value())
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + bookId.value(), this.getClass().getSimpleName()));

        Book existingBook = BookMapper.toDomain(existingEntity);
        Book updatedBook = Book.update(existingBook, BookMapper.toDomain(bookDto));
        Book savedBook = BookMapper.toDomain(bookRepository.save(BookMapper.toEntity(updatedBook)));

        return BookMapper.toDto(savedBook);
    }
}
