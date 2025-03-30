package com.laur.bookshop.controllers;

import com.laur.bookshop.config.dto.AppUserDTO;
import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.model.LoginRequest;
import com.laur.bookshop.services.AppUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
public class AppUserController {
    private final AppUserService service;

    @GetMapping("/app_users/all")
    public List<AppUser> getAllUsers() {
        return service.findAllUsers();
    }

    @PostMapping("/app_users/add")
    public AppUser addUser(@Valid @RequestBody AppUserDTO validatedUser) {
        return service.addAppUser(validatedUser);
    }

    @DeleteMapping("/app_users/delete")
    public ResponseEntity<String> deleteAppUsers(@RequestBody Map<String, List<UUID>> ids) {
        List<UUID> idList = ids.get("ids");
        return service.deleteByIds(idList);
    }

    @PutMapping("/app_users/edit")
    public AppUser updateAppUser(@RequestBody AppUser appUser) {
        return service.updateAppUser(appUser);
    }

    @PostMapping("/app_users/login")
    public AppUser login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

}
