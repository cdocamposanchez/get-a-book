package com.adi.gab.application.usecase.book;

import com.adi.gab.application.dto.BookDTO;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.mapper.BookMapper;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.BookRepository;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import com.adi.gab.infrastructure.security.AuthenticatedUserProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GetFavoritesUseCase {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public GetFavoritesUseCase(UserRepository userRepository, BookRepository bookRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public List<BookDTO> execute() {
        UserId userId = authenticatedUserProvider.getAuthenticatedUserId();
        Optional<UserEntity> userEntity = userRepository.findById(userId.value());
        if (userEntity.isEmpty()) throw new ApplicationException(
                "User with ID: " + userId.value() + " not found",
                this.getClass().getSimpleName());

        List<BookDTO> favorites = new ArrayList<>();

        userEntity.get().getFavorites().forEach(favorite -> {
            Optional<BookEntity> book = bookRepository.findById(favorite);
            if (book.isEmpty()) throw new ApplicationException(
                    "Book with ID: " + favorite + " not found",
                    this.getClass().getSimpleName());

            favorites.add(BookMapper.toDto(book.get()));
        });

        return favorites;
    }
}
