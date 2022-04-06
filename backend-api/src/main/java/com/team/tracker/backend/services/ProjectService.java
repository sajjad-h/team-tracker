package com.team.tracker.backend.services;

import java.util.List;
import java.util.Optional;

import com.team.tracker.backend.models.Project;

public interface ProjectService {
    void save(Project project);

    Optional<Project> findById(String id);

    List<Project> findByOwner(String owner);
}
