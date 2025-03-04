package com.laur.bookshop.repository.data;

import com.laur.bookshop.model.data.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublisherRepository extends JpaRepository<Publisher, UUID> {
}
