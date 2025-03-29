package com.laur.bookshop.services;

import com.laur.bookshop.config.exceptions.AppUserNotFoundException;
import com.laur.bookshop.config.exceptions.InvalidPassword;
import com.laur.bookshop.config.validators.model.AppUserValidator;
import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.model.LoginRequest;
import com.laur.bookshop.repositories.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService {
    private final AppUserRepo appUserRepo;

    public List<AppUser> findAllUsers() {
        return appUserRepo.findAll();
    }

    public AppUser addAppUser(AppUserValidator u) {
        if (appUserRepo.findByUsername(u.getUsername()).isPresent())
            throw new AppUserNotFoundException(u.getUsername() + " already exists!");
        AppUser user = new AppUser();
        user.setUsername(u.getUsername());
        user.setPassword(u.getPassword());
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setRole(u.getRole());
        return appUserRepo.save(user);
    }

    public ResponseEntity<String> deleteByIds(List<UUID> ids) {
        if (ids == null || ids.isEmpty())
            return ResponseEntity.badRequest().body("No IDs provided!");
        for (UUID i : ids)
            appUserRepo.deleteById(i);
        return ResponseEntity.ok("App User deleted successfully!");
    }

    public AppUser updateAppUser(AppUser u) {
        AppUser user = appUserRepo.findById(u.getId()).orElseThrow(
                () -> new AppUserNotFoundException(u.getUsername() + " does not exist!")
        );
        user.setUsername(u.getUsername());
        user.setPassword(u.getPassword());
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setRole(u.getRole());
        return appUserRepo.save(user);
    }

    public AppUser login(LoginRequest lr) {
        AppUser appUser = appUserRepo.findByUsername(lr.getUsername()).orElseThrow(
                () -> new AppUserNotFoundException("No user found with username: '" + lr.getUsername() + "'")
        );
        if(!appUser.getPassword().equals(lr.getPassword()))
            throw new InvalidPassword("Wrong password for username: '" + lr.getUsername() + "'");
        return appUser;
    }
}
