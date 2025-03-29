package com.laur.bookshop.repositories;

import com.laur.bookshop.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepo extends JpaRepository<Author, UUID> {
}
