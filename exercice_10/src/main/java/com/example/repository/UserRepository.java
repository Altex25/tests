package com.example.repository;

import com.example.model.User;

import java.util.Optional;

public interface UserRepository {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    void save(User user);
}
