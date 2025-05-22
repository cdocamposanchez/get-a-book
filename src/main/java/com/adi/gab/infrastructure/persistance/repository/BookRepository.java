package com.adi.gab.infrastructure.persistance.repository;

import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, UUID>, JpaSpecificationExecutor<BookEntity> {
    @NotNull
    Optional<BookEntity> findById(@NotNull UUID id);

    Boolean existsByTitleIgnoreCase(String title);
}
