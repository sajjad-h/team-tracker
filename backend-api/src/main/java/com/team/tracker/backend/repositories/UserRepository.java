package com.team.tracker.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import com.team.tracker.backend.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
