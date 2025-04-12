package com.laur.bookshop.services;

import com.laur.bookshop.config.enums.Role;
import com.laur.bookshop.config.exceptions.AppUserNotFoundException;
import com.laur.bookshop.config.exceptions.DuplicateException;
import com.laur.bookshop.config.exceptions.InvalidPassword;
import com.laur.bookshop.config.dto.AppUserDTO;
import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.model.LoginRequest;
import com.laur.bookshop.repositories.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.laur.bookshop.config.enums.AppMessages.*;

@Service
@AllArgsConstructor
public class AppUserService {
    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    public List<AppUser> findAllUsers() {
        return appUserRepo.findAll();
    }

    public AppUser addAppUser(AppUserDTO u) {
        if (appUserRepo.findByUsername(u.getUsername()).isPresent())
            throw new DuplicateException(USER_DUPLICATE_MESSAGE);
        AppUser user = new AppUser();
        user.setUsername(u.getUsername());
        user.setPassword(passwordEncoder.encode(u.getPassword()));
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setRole(Role.valueOf(u.getRole()));
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
        user.setPassword(passwordEncoder.encode(u.getPassword()));
        user.setUsername(u.getUsername());
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setRole(Role.valueOf(u.getRole()));
        return appUserRepo.save(user);
    }

    public AppUser login(LoginRequest lr) {
        AppUser appUser = appUserRepo.findByUsername(lr.getUsername()).orElseThrow(
                () -> new AppUserNotFoundException(USER_NOT_FOUND_MESSAGE)
        );
        if(!passwordEncoder.matches(lr.getPassword(), appUser.getPassword()))
            throw new InvalidPassword(WRONG_PASSWORD_MESSAGE);
        return appUser;
    }

    public List<AppUser> findUsersByCriteria(String username, String role, String firstName, String lastName) {
        Specification<AppUser> spec = Specification.where(null); // Start with a null specification

        if (username != null && !username.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), username));
        }
        if (role != null && !role.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("role"), role));
        }
        if (firstName != null && !firstName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("firstName"), firstName));
        }
        if (lastName != null && !lastName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("lastName"), lastName));
        }

        return appUserRepo.findAll(spec);
    }
}
