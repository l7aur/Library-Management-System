package com.laur.bookshop.repository.data;

import com.laur.bookshop.model.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByTitle(String title);

    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
    Optional<Book> findByISBN(@Param("isbn") String isbn);

    @Query("SELECT b FROM Book b WHERE :author MEMBER OF b.authors")
    Optional<Book> findByAuthor(@Param("author") String author);

    @Modifying
    @Query("DELETE FROM Book b WHERE b.isbn = :isbn")
    void deleteByISBN(@Param("isbn") String isbn);

    @Modifying
    @Query("DELETE FROM Book b WHERE b.isbn = :title")
    void deleteByTitle(@Param("title") String title);
}
