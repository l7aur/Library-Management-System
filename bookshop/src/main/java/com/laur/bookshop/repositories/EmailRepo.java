package com.laur.bookshop.repositories;

import com.laur.bookshop.model.EmailDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmailRepo extends JpaRepository<EmailDetails, UUID> {
    @Query("SELECT e FROM EmailDetails e WHERE e.receiver = :receiver ORDER BY e.expirationTime DESC")
    List<Optional<EmailDetails>> findByReceiver(@Param("receiver") String receiver);

    @Modifying
    @Transactional
    @Query("DELETE FROM EmailDetails e WHERE e.receiver = :receiver")
    void deleteByReceiver(String receiver);
}
