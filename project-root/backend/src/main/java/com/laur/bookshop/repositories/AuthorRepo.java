package com.laur.bookshop.repositories;

import com.laur.bookshop.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepo extends JpaRepository<Author, UUID> {

    @Query("SELECT a FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName")
    Optional<Author> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}

