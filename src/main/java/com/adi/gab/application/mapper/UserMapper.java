package com.adi.gab.application.mapper;

import com.adi.gab.application.dto.UserDTO;
import com.adi.gab.domain.model.User;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;

import java.util.ArrayList;
import java.util.HashSet;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId().value())
                .firstName(user.getFirstName())
                .lastNames(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .favorites(new HashSet<>(UserFavoritesMapper.toEntity(user.getFavorites())))
                .build();
    }

    public static User toDomain(UserEntity entity) {
        return User.builder()
                .id(UserId.of(entity.getId()))
                .firstName(entity.getFirstName())
                .lastName(entity.getLastNames())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .favorites(UserFavoritesMapper.toDomain(new ArrayList<>(entity.getFavorites())))
                .build();
    }

    public static User toDomain(UserDTO dto) {
        return User.builder()
                .id(UserId.of(dto.getId()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .favorites(UserFavoritesMapper.toDomain(dto.getFavorites()))
                .build();
    }

    public static UserDTO toDTO(User user) {
        return UserDTO
                .builder()
                .id(user.getId().value())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .favorites(UserFavoritesMapper.toEntity(user.getFavorites()))
                .build();
    }

    private UserMapper() {}
}
