package com.adi.gab.infrastructure.persistance.repository;

import com.adi.gab.infrastructure.persistance.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    
    @NotNull
    Optional<UserEntity> findById(@NotNull UUID id);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.favorites WHERE u.id = :id")
    Optional<UserEntity> findByIdWithFavorites(@Param("id") UUID id);

    Boolean existsByEmail(String email);
}
