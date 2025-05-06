package com.adi.gab.application.usecase.book;

import com.adi.gab.domain.model.Book;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.dto.BookDTO;
import com.adi.gab.infrastructure.mapper.BookMapper;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateBookUseCase {
    private final BookRepository bookRepository;

    public CreateBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO execute(BookDTO bookDto) {
        Book newBook = Book.create(
                Book.builder()
                        .id(BookId.generate())
                        .title(bookDto.getTitle())
                        .description(bookDto.getDescription())
                        .publisher(bookDto.getPublisher())
                        .qualification(bookDto.getQualification())
                        .categories(bookDto.getCategories())
                        .quantity(bookDto.getQuantity())
                        .price(bookDto.getPrice())
                        .year(bookDto.getYear())
                        .build()
        );
        Book createdBook = BookMapper.toDomain(bookRepository.save(BookMapper.toEntity(newBook)));
        return BookMapper.toDto(createdBook);
    }
}
