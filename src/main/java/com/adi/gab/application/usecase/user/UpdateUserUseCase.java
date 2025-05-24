package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.UserDTO;
import com.adi.gab.application.dto.request.UpdateEmailRequest;
import com.adi.gab.application.exception.ApplicationException;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.UserMapper;
import com.adi.gab.domain.model.User;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import com.adi.gab.infrastructure.security.AuthenticatedUserProvider;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final Cache cache;

    public UpdateUserUseCase(UserRepository userRepository, AuthenticatedUserProvider authenticatedUserProvider, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.cache = cacheManager.getCache("recoverCodes");
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

    @Transactional
    public void updateEmail(UpdateEmailRequest request) {
        if (Boolean.FALSE.equals(userRepository.existsByEmail(request.getOldEmail())))
            throw new ApplicationException("No user found with email: " + request.getOldEmail(), this.getClass().getSimpleName());

        String cachedCode = cache.get(request.getOldEmail(), String.class);
        if (cachedCode == null || !cachedCode.equalsIgnoreCase(request.getRecoveryCode())) {
            throw new ApplicationException("Invalid or expired recovery code.", this.getClass().getSimpleName());
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getNewEmail()))) {
            throw new ApplicationException("Email already in use: " + request.getNewEmail(), this.getClass().getSimpleName());
        }

        UserId userId = authenticatedUserProvider.getAuthenticatedUserId();
        UserEntity user = userRepository.findById(userId.value())
                .orElseThrow(() -> new NotFoundException("User not found", this.getClass().getSimpleName()));

        user.setEmail(request.getNewEmail());
        userRepository.save(user);

        cache.evict(request.getOldEmail());
    }

}
