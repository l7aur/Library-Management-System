package com.laur.bookshop.model;

import com.laur.bookshop.config.dto.AppUserDTO;
import com.laur.bookshop.config.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name= "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name= "role", nullable = false)
    private Role role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "e-mail")
    private String email;

    public AppUserDTO toDTO() {
        AppUserDTO dto = new AppUserDTO();
        dto.setId(this.id);
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setPassword(this.password);
        dto.setRole(this.role.toString());
        dto.setUsername(this.username);
        dto.setEmail(this.email);
        return dto;
    }
}
