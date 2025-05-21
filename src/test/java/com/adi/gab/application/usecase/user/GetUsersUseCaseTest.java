package com.adi.gab.application.usecase.user;

import com.adi.gab.application.dto.PaginationRequest;
import com.adi.gab.application.dto.UserDTO;
import com.adi.gab.application.exception.NotFoundException;
import com.adi.gab.application.usecase.user.GetUsersUseCase;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import com.adi.gab.infrastructure.persistance.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetUsersUseCaseTest {

    private UserRepository userRepository;
    private GetUsersUseCase getUsersUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        getUsersUseCase = new GetUsersUseCase(userRepository);
    }

    private UserEntity sampleUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setFirstName("Alice");
        entity.getLastNames();
        entity.setEmail("alice@gmail.com");
        entity.setPassword("securePass123");
        return entity;
    }

    @Test
    void shouldReturnPaginatedUsers() {
        PaginationRequest pagination = new PaginationRequest(0, 5);
        List<UserEntity> userEntities = List.of(sampleUserEntity());

        when(userRepository.findAll(PageRequest.of(0, 5)))
                .thenReturn(new PageImpl<>(userEntities));

        List<UserDTO> result = getUsersUseCase.execute(pagination);

        assertEquals(1, result.size());
        assertEquals("alice@gmail.com", result.get(0).getEmail());
        verify(userRepository).findAll(PageRequest.of(0, 5));
    }

    @Test
    void shouldReturnUserById_WhenExists() {
        UserEntity entity = sampleUserEntity();
        UUID userId = entity.getId();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(entity));

        UserDTO result = getUsersUseCase.getByUserId(new UserId(userId));

        assertEquals("alice@gmail.com", result.getEmail());
        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowNotFoundException_WhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> getUsersUseCase.getByUserId(new UserId(userId)));

        assertTrue(ex.getMessage().contains("User not found with ID: " + userId));
        verify(userRepository).findById(userId);
    }
}

