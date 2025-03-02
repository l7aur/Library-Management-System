package com.laur.demo.repository.user;

import com.laur.demo.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
}
