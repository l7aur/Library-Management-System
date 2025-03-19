package com.laur.bookshop.controller;

import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.dto.AppUserCreateDTO;
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
    private final AppUserService appUserService;

    @GetMapping("/app_users/all")
    public List<AppUser> getAllUsers() {
        return appUserService.findAllUsers();
    }

    @PostMapping("/app_users/add")
    public AppUser addUser(@Valid @RequestBody AppUserCreateDTO user) {
        return appUserService.addAppUser(user);
    }

    @DeleteMapping("/app_users/delete")
    public ResponseEntity<String> deleteAppUsersById(@RequestBody Map<String, List<UUID>> ids) {
        List<UUID> idList = ids.get("ids");
        return appUserService.deleteAppUsersById(idList);
    }

    @PostMapping("/app_users/login")
    public AppUser login(@RequestBody LoginRequest request) {
        return appUserService.login(request);
    }

//    @GetMapping("/app_users/id/{id}")
//    public AppUser getUserById(@PathVariable UUID id) {
//        return appUserService.findUserById(id);
//    }
//
//    @GetMapping("app_users/username/{username}")
//    public AppUser getUserByUsername(@PathVariable String username) {
//        return appUserService.findUserByUsername(username);
//    }
}
