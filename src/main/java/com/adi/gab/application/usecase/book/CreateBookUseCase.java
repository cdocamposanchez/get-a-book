package com.adi.gab.application.usecase.book;

import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.domain.model.Book;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.mapper.BookMapper;
import com.adi.gab.infrastructure.config.cloudinary.CloudinaryService;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CreateBookUseCase {
    private final BookRepository bookRepository;
    private final CloudinaryService cloudinaryService;

    public CreateBookUseCase(BookRepository bookRepository, CloudinaryService cloudinaryService) {
        this.bookRepository = bookRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public BookDTO execute(BookDTO bookDto) {
        if (Boolean.TRUE.equals(bookRepository.existsByTitleIgnoreCase(bookDto.getTitle()))) {
            throw new ApplicationException(
                    "A book with this title already exists: " + bookDto.getTitle(),
                    this.getClass().getSimpleName()
            );
        }

        int maxRetries = 3;
        int attempts = 0;

        while (true) {
            try {
                String imageUrl = cloudinaryService.uploadImage(bookDto.getImage());
                Book newBook = buildBookFromDto(bookDto, imageUrl);
                return saveBook(newBook);
            } catch (DataIntegrityViolationException _) {
                attempts++;
                if (attempts >= maxRetries) {
                    throw new ApplicationException(
                            "Failed to create Book due to ID collision after multiple attempts.",
                            this.getClass().getSimpleName()
                    );
                }
            }
        }
    }

    private Book buildBookFromDto(BookDTO bookDto, String imageUrl) {
        return Book.create(
                Book.builder()
                        .id(BookId.generate())
                        .title(bookDto.getTitle())
                        .description(bookDto.getDescription())
                        .publisher(bookDto.getPublisher())
                        .qualification(bookDto.getQualification())
                        .categories(bookDto.getCategories())
                        .quantity(bookDto.getQuantity())
                        .price(bookDto.getPrice())
                        .imageUrl(imageUrl)
                        .year(bookDto.getYear())
                        .build()
        );
    }

    private BookDTO saveBook(Book book) {
        try {
            Book savedDomain = BookMapper.toDomain(bookRepository.save(BookMapper.toEntity(book)));
            return BookMapper.toDto(savedDomain);
        } catch (Exception e) {
            throw new ApplicationException("Error saving book: " + e.getMessage(), this.getClass().getSimpleName());
        }
    }
}
