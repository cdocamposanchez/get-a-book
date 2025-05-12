package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.PaginationRequest;
import com.adi.gab.application.dto.UserDTO;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.mapper.UserMapper;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUsersUseCase {

    private final UserRepository userRepository;

    public GetUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> execute(PaginationRequest pagination) {
        return userRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize()))
                .stream()
                .map(UserMapper::toDomain)
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserDTO getByUserId(UserId userId) {
        return userRepository.findById(userId.value())
                .map(UserMapper::toDomain)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId.value(), this.getClass().getSimpleName()));
    }
}
