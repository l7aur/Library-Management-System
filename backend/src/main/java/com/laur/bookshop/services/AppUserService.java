package com.laur.bookshop.services;

import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.model.AppUserDTO;
import com.laur.bookshop.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public List<AppUser> findAllUsers() {
        return appUserRepository.findAll();
    }

    public AppUser findUserById(UUID id) {
        return appUserRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("No user found with id: " + id)
        );
    }

    public AppUser findUserByUsername(String username) {
        return appUserRepository.findByUsername(username).orElseThrow(
                () -> new IllegalStateException("No user found with username: " + username)
        );
    }

    public AppUser addAppUser(AppUserDTO user) {
        if(appUserRepository.findByUsername(user.getUsername()).isPresent())
            throw new IllegalArgumentException(user.getUsername() + " already exists");
        AppUser newAppUser = new AppUser();
        newAppUser.setUsername(user.getUsername());
        newAppUser.setPassword(user.getPassword());
        newAppUser.setFirstName(user.getFirstName());
        newAppUser.setLastName(user.getLastName());
        newAppUser.setRole(user.getRole());
        return appUserRepository.save(newAppUser);
    }
}
