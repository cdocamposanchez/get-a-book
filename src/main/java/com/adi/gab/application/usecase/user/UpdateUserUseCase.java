package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.UserDTO;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.UserMapper;
import com.adi.gab.domain.model.User;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import com.adi.gab.infrastructure.security.AuthenticatedUserProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public UpdateUserUseCase(UserRepository userRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.userRepository = userRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

     @Transactional
    public UserDTO execute(UserDTO userDTO) {
        UserId userId = authenticatedUserProvider.getAuthenticatedUserId();
        Optional<UserEntity> oldEntity = userRepository.findById(userId.value());

        if (oldEntity.isEmpty())
            throw new NotFoundException("Order not found with ID: " + userDTO.getId(), this.getClass().getSimpleName());

        userDTO.setId(userId.value());

        User newEntity = User.update(UserMapper.toDomain(oldEntity.get()), UserMapper.toDomain(userDTO));
        UserEntity savedEntity = userRepository.save(UserMapper.toEntity(newEntity));
        return UserMapper.toDTO(UserMapper.toDomain(savedEntity));
    }
}
