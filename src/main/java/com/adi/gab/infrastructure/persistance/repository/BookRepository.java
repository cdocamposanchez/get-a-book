package com.adi.gab.infrastructure.persistance.repository;

import com.adi.gab.infrastructure.persistance.entity.BookEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, UUID> {
    @NotNull
    Optional<BookEntity> findById(@NotNull UUID id);

    Page<BookEntity> findByCategoriesContainingIgnoreCase(String category, PageRequest pageRequest);

    Page<BookEntity> findByPublisherContainingIgnoreCase(String publisher, PageRequest pageRequest);

    Page<BookEntity> findByYear(Integer year, PageRequest pageRequest);

    Page<BookEntity> findByTitleContainingIgnoreCase(String regex, PageRequest pageRequest);

    Boolean existsByTitleIgnoreCase(String title);
}
