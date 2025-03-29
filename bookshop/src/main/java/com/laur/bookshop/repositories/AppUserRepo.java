package com.laur.bookshop.repositories;

import com.laur.bookshop.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepo extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);
}
