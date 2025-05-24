package com.adi.gab.application.usecase.user;

import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import com.adi.gab.infrastructure.security.AuthenticatedUserProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UpdateFavoriteUseCase {

    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public UpdateFavoriteUseCase(UserRepository userRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.userRepository = userRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public String execute(BookId bookId) {
        UserId userId = authenticatedUserProvider.getAuthenticatedUserId();
        Optional<UserEntity> oldUserEntity = userRepository.findById(userId.value());
        if (oldUserEntity.isEmpty()) throw new ApplicationException(
                "User with ID: " + userId.value() + " not found",
                this.getClass().getSimpleName());

        UserEntity newUserEntity = oldUserEntity.get();

        String response = "";
        if (newUserEntity.getFavorites().contains(bookId.value())) {
            newUserEntity.getFavorites().remove(bookId.value());
            response = "Removed";
        } else {
            newUserEntity.getFavorites().add(bookId.value());
            response = "Added";
        }

        try {
            userRepository.saveAndFlush(newUserEntity);
        } catch (Exception e) {
            throw new ApplicationException("Error during saving: " + e.getMessage(), this.getClass().getSimpleName());
        }

        return response;
    }
}
