package com.adi.gab.application.usecase.user;

import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UserId userId) {
        if (!userRepository.existsById(userId.value()))
            throw new NotFoundException("User not found with ID: " + userId.value(), this.getClass().getSimpleName());

        userRepository.deleteById(userId.value());
    }
}
