package com.team.tracker.backend.services;

import java.util.Optional;

import com.team.tracker.backend.models.User;

public interface UserService {
    void save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
