package com.laur.bookshop.repository;

import com.laur.bookshop.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByTitle(String title);
    Optional<Book> findByIsbn(@Param("isbn") String isbn);

    @Query("SELECT b FROM Book b WHERE :author MEMBER OF b.authors")
    Optional<List<Book>> findByAuthor(@Param("author") String author);

    @Transactional
    @Modifying
    @Query("DELETE FROM Book b WHERE b.isbn = :isbn")
    void deleteByIsbn(@Param("isbn") String isbn);

    @Transactional
    @Modifying
    @Query("DELETE FROM Book b WHERE b.isbn = :title")
    void deleteByTitle(@Param("title") String title);
}
