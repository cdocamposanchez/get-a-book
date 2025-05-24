package com.adi.gab.application.usecase.book;

import com.adi.gab.application.dto.request.BookFilterRequest;
import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.dto.response.PageResponse;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.BookMapper;
import com.adi.gab.domain.types.SortOrder;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import com.adi.gab.infrastructure.persistance.specification.BookSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBooksUseCase {

    private final BookRepository bookRepository;

    public GetBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public PageResponse<BookDTO> execute(BookFilterRequest request) {
        SortOrder order = SortOrder.fromString(request.getSortOrder());
        Sort sort = order == SortOrder.DESC ? Sort.by("title").descending() : Sort.by("title").ascending();

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(), sort);
        var page = bookRepository.findAll(BookSpecification.withFilters(request), pageRequest);

        List<BookDTO> content = page.getContent()
                .stream()
                .map(BookMapper::toDomain)
                .map(BookMapper::toDto)
                .toList();

        return new PageResponse<>(
                content,
                request.getPage(),
                request.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public BookDTO getById(BookId id) {
        return bookRepository.findById(id.value())
                .map(BookMapper::toDomain)
                .map(BookMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Id not found with id" + id, this.getClass().getSimpleName()));
    }
}
