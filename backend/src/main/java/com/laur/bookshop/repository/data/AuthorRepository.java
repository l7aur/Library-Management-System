package com.laur.bookshop.repository.data;

import com.laur.bookshop.model.data.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
