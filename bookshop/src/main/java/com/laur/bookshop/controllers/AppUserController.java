package com.laur.bookshop.controllers;

import com.laur.bookshop.config.dto.AppUserDTO;
import com.laur.bookshop.config.security.JwtUtil;
import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.model.LoginRequest;
import com.laur.bookshop.model.LoginResponse;
import com.laur.bookshop.services.AppUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
public class AppUserController {
    private final AppUserService service;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

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
    public AppUser updateAppUser(@Valid @RequestBody AppUserDTO appUser) {
        return service.updateAppUser(appUser);
    }

    @PostMapping("/app_users/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtil.generateToken(authentication);
            LoginResponse response = new LoginResponse(service.login(request), null, token);
            return ResponseEntity.ok(response);
        }
        catch (BadCredentialsException ex) {
            return ResponseEntity
                    .status(401)
                    .body(new LoginResponse(null, "Invalid username or password", null));
        }
        catch (Exception ex) {
            return ResponseEntity
                    .status(500)
                    .body(new LoginResponse(null, "Internal server error", null));
        }
    }

    @GetMapping("app_users/filter")
    public List<AppUser> filterUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        return service.findUsersByCriteria(username, role, firstName, lastName);
    }
}
