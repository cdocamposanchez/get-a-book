package com.adi.gab.infrastructure.mapper;

import com.adi.gab.domain.model.User;
import com.adi.gab.domain.valueobject.UserId;
import com.adi.gab.infrastructure.persistance.entity.UserEntity;

import java.util.List;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId().value())
                .firstName(user.getFirstName())
                .lastNames(user.getLastNames())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static User toDomain(UserEntity entity) {
        return User.builder()
                .id(new UserId(entity.getId()))
                .firstName(entity.getFirstName())
                .lastNames(entity.getLastNames())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .build();
    }

    public static List<User> toDomainList(List<UserEntity> entities) {
        return entities.stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    public static List<UserEntity> toEntityList(List<User> users) {
        return users.stream()
                .map(UserMapper::toEntity)
                .toList();
    }
}
