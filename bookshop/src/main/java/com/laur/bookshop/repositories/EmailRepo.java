package com.laur.bookshop.repositories;

import com.laur.bookshop.model.EmailDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmailRepo extends JpaRepository<EmailDetails, UUID> {
    @Query("SELECT e FROM EmailDetails e WHERE e.receiver = :email ORDER BY e.expirationTime DESC")
    List<Optional<EmailDetails>> findByEmail(@Param("email") String email);

    void deleteByReceiver(String receiver);
}
