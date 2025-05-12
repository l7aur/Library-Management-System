package com.laur.bookshop.controllers;

import com.laur.bookshop.config.dto.AppUserDTO;
import com.laur.bookshop.config.exceptions.EmailNotFoundException;
import com.laur.bookshop.config.exceptions.ExpiredSecurityCodeException;
import com.laur.bookshop.config.security.JwtUtil;
import com.laur.bookshop.model.*;
import com.laur.bookshop.repositories.AppUserRepo;
import com.laur.bookshop.services.AppUserService;
import com.laur.bookshop.services.EmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
public class AppUserController {
    private final AppUserService service;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AppUserRepo appUserRepo;

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

    @PostMapping("/app_users/change_password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        if(!changePasswordRequest.password().equals(changePasswordRequest.confirmation()))
            return ResponseEntity.status(401).body("Password and confirmation mismatch!");
        EmailDetails emailDetails = emailService.findByEmail(changePasswordRequest.email()).orElseThrow(EmailNotFoundException::new);
        if(emailDetails.getExpirationTime().isBefore(LocalTime.now()))
            throw new ExpiredSecurityCodeException();
        if(!emailDetails.getCode().equals(changePasswordRequest.securityCode()))
            throw new ExpiredSecurityCodeException();
        AppUser user = appUserRepo.findByEmail(changePasswordRequest.email()).orElseThrow(EmailNotFoundException::new);
        user.setPassword(changePasswordRequest.password());
        emailService.delete(emailDetails.getId());
        return service.updateAppUser(user.toDTO()) != null
                ? ResponseEntity.status(200).body("ok")
                : ResponseEntity.status(501).body("error");
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
