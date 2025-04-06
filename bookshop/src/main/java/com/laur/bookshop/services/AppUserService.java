package com.laur.bookshop.services;

import com.laur.bookshop.config.exceptions.AppUserNotFoundException;
import com.laur.bookshop.config.exceptions.DuplicateException;
import com.laur.bookshop.config.exceptions.InvalidPassword;
import com.laur.bookshop.config.dto.AppUserDTO;
import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.model.LoginRequest;
import com.laur.bookshop.repositories.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.laur.bookshop.config.enums.AppMessages.*;

@Service
@AllArgsConstructor
public class AppUserService {
    private final AppUserRepo appUserRepo;

    public List<AppUser> findAllUsers() {
        return appUserRepo.findAll();
    }

    public AppUser addAppUser(AppUserDTO u) {
        if (appUserRepo.findByUsername(u.getUsername()).isPresent())
            throw new DuplicateException(USER_DUPLICATE_MESSAGE);
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
            return ResponseEntity.badRequest().body(USER_DELETE_ERROR_MESSAGE);
        for (UUID i : ids)
            appUserRepo.deleteById(i);
        return ResponseEntity.ok(USER_DELETE_SUCCESS_MESSAGE);
    }

    public AppUser updateAppUser(AppUserDTO u) {
        AppUser user = appUserRepo.findById(u.getId()).orElseThrow(
                () -> new AppUserNotFoundException(USER_NOT_FOUND_MESSAGE)
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
                () -> new AppUserNotFoundException(USER_NOT_FOUND_MESSAGE)
        );
        if(!appUser.getPassword().equals(lr.getPassword()))
            throw new InvalidPassword(WRONG_PASSWORD_MESSAGE);
        return appUser;
    }
}
