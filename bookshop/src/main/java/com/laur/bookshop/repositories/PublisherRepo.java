package com.laur.bookshop.repositories;

import com.laur.bookshop.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PublisherRepo extends JpaRepository<Publisher, UUID> {
    Optional<Publisher> findByName(String name);
}
