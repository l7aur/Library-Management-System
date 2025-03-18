package com.laur.bookshop.services;

import com.laur.bookshop.config.exceptions.AppUserInvalidPassword;
import com.laur.bookshop.config.exceptions.AppUserNotFoundException;
import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.dto.AppUserDTO;
import com.laur.bookshop.model.LoginRequest;
import com.laur.bookshop.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    public AppUser addAppUser(AppUserDTO user) {
        if(appUserRepository.findByUsername(user.getUsername()).isPresent())
            throw new AppUserNotFoundException("User '" + user.getUsername() + "' already exists");
        AppUser newAppUser = new AppUser();
        newAppUser.setUsername(user.getUsername());
        newAppUser.setPassword(user.getPassword());
        newAppUser.setFirstName(user.getFirstName());
        newAppUser.setLastName(user.getLastName());
        newAppUser.setRole(user.getRole());
        return appUserRepository.save(newAppUser);
    }

    public ResponseEntity<String> deleteAppUsersById(List<UUID> ids) {
        if (ids == null || ids.isEmpty())
            return ResponseEntity.badRequest().body("No IDs provided");
        for(UUID i : ids)
            appUserRepository.deleteById(i);
        return ResponseEntity.ok("App User deleted successfully");
    }

    public AppUser login(LoginRequest request) {
        AppUser appUser = appUserRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new AppUserNotFoundException("No user found with username: '" + request.getUsername() + "'")
        );
        if(!appUser.getPassword().equals(request.getPassword()))
            throw new AppUserInvalidPassword("Wrong password for username: '" + request.getUsername() + "'");
        return appUser;
    }

//    public AppUser findUserById(UUID id) {
//        return appUserRepository.findById(id).orElseThrow(
//                () -> new AppUserNotFoundException("No user found with id: " + id)
//        );
//    }
//
//    public AppUser findUserByUsername(String username) {
//        return appUserRepository.findByUsername(username).orElseThrow(
//                () -> new AppUserNotFoundException("No user found with username: " + username)
//        );
//    }
}
