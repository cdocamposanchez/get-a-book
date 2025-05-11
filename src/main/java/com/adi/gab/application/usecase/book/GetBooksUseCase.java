package com.adi.gab.application.usecase.book;

import com.adi.gab.application.dto.PaginationRequest;
import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.BookMapper;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetBooksUseCase {

    private final BookRepository bookRepository;

    public GetBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> execute(PaginationRequest pagination) {
        return bookRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize()))
                .stream()
                .map(BookMapper::toDomain)
                .map(BookMapper::toDto)
                .toList();
    }

    public List<BookDTO> getByCategory(String category, PaginationRequest pagination) {
        return bookRepository.findByCategoriesContainingIgnoreCase(category, PageRequest.of(pagination.getPage(), pagination.getSize()))
                .stream()
                .map(BookMapper::toDomain)
                .map(BookMapper::toDto)
                .toList();
    }

    public List<BookDTO> getByPublisher(String publisher, PaginationRequest pagination) {
        return bookRepository.findByPublisherContainingIgnoreCase(publisher, PageRequest.of(pagination.getPage(), pagination.getSize()))
                .stream()
                .map(BookMapper::toDomain)
                .map(BookMapper::toDto)
                .toList();
    }

    public List<BookDTO> getByYear(Integer year, PaginationRequest pagination) {
        return bookRepository.findByYear(year, PageRequest.of(pagination.getPage(), pagination.getSize()))
                .stream()
                .map(BookMapper::toDomain)
                .map(BookMapper::toDto)
                .toList();
    }

    public List<BookDTO> getByRegex(String titleRegex, PaginationRequest pagination) {
        return bookRepository.findByTitleContainingIgnoreCase(titleRegex, PageRequest.of(pagination.getPage(), pagination.getSize()))
                .stream()
                .map(BookMapper::toDomain)
                .map(BookMapper::toDto)
                .toList();
    }

    public BookDTO getById(BookId id) {
        return bookRepository.findById(id.value())
                .map(BookMapper::toDomain)
                .map(BookMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Id not found with id" + id, this.getClass().getSimpleName()));
    }
}
