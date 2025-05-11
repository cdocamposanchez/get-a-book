package com.adi.gab.application.usecase.book;

import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.BookMapper;
import com.adi.gab.domain.model.Book;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class ReduceStockUseCase {
    private final BookRepository bookRepository;

    public ReduceStockUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO execute(BookId bookId, int quantityToReduce) {

        BookEntity existingEntity = bookRepository.findById(bookId.value())
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + bookId.value(), this.getClass().getSimpleName()));

        if (existingEntity.getQuantity() < quantityToReduce) throw new ApplicationException(
                "Not enough items in book with ID: " + existingEntity.getId() + " available: " + existingEntity.getQuantity(),
                this.getClass().getSimpleName());

        existingEntity.setQuantity(existingEntity.getQuantity() - quantityToReduce);

        Book savedBook = BookMapper.toDomain(bookRepository.save(existingEntity));

        return BookMapper.toDto(savedBook);
    }
}
