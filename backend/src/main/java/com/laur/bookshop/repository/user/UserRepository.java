package com.laur.bookshop.repository.user;

import com.laur.bookshop.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
}
