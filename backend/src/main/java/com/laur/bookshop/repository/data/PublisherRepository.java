package com.laur.bookshop.repository.data;

import com.laur.bookshop.model.data.Publisher;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PublisherRepository extends JpaRepository<Publisher, UUID> {
    Optional<Publisher> findByName(String name);
    Optional<List<Publisher>> findByLocation(String location);
    Optional<List<Publisher>> findByFoundingYear(int foundingYear);

    @Transactional
    @Modifying
    @Query("DELETE FROM Publisher p WHERE p.name = :name")
    void deleteByName(@Param("name") String name);
}
