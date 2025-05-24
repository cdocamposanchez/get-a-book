package com.adi.gab.application.usecase.book;

import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.BookMapper;
import com.adi.gab.domain.model.Book;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.config.cloudinary.CloudinaryService;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateBookUseCase {
    private final BookRepository bookRepository;
    private final CloudinaryService cloudinaryService;


    public UpdateBookUseCase(BookRepository bookRepository, CloudinaryService cloudinaryService) {
        this.bookRepository = bookRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public BookDTO execute(BookId bookId, BookDTO bookDto) {
        BookEntity existingEntity = bookRepository.findById(bookId.value())
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + bookId.value(), this.getClass().getSimpleName()));

        if (bookDto.getTitle().isEmpty()) {
            bookDto.setTitle(existingEntity.getTitle());
        }

        if (!existingEntity.getTitle().equals(bookDto.getTitle()) && Boolean.TRUE.equals(bookRepository.existsByTitleIgnoreCase(bookDto.getTitle())) ) {
                throw new ApplicationException(
                        "A book with this title already exists: " + bookDto.getTitle(),
                        this.getClass().getSimpleName()
                );
            }


        String imageUrl = existingEntity.getImageUrl();

        if (bookDto.getImage() != null && !bookDto.getImage().isEmpty()) {
            try {
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    cloudinaryService.deleteImage(imageUrl);
                }

                imageUrl = cloudinaryService.uploadImage(bookDto.getImage());
                bookDto.setImageUrl(imageUrl);

            } catch (Exception e) {
                throw new ApplicationException(
                        "Failed to upload new image for book with title: " + bookDto.getTitle() +
                                ". Reason: " + e.getMessage(),
                        this.getClass().getSimpleName()
                );
            }
        }

        Book updatedBook = Book.update(BookMapper.toDomain(existingEntity), BookMapper.toDomain(bookDto));

        Book savedBook = BookMapper.toDomain(bookRepository.save(BookMapper.toEntity(updatedBook)));

        return BookMapper.toDto(savedBook);
    }

}
