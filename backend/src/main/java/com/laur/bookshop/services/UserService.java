package com.laur.bookshop.services;

import com.laur.bookshop.model.User;
import com.laur.bookshop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("No user found with id: " + id)
        );
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalStateException("No user found with username: " + username)
        );
    }
}
