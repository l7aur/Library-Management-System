package com.laur.bookshop.repository;

import com.laur.bookshop.model.Author;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Optional<List<Author>> findByFirstName(String firstName);
    Optional<List<Author>> findByLastName(String lastName);
    Optional<List<Author>> findByNationality(String nationality);
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

    @Transactional
    @Modifying
    @Query("DELETE FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName")
    void deleteByFirstNameAndLastName(String firstName, String lastName);
}
