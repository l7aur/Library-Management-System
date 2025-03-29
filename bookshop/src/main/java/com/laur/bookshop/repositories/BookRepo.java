package com.laur.bookshop.repositories;

import com.laur.bookshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookRepo extends JpaRepository<Book, UUID> {
    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findByTitle(String title);
}
