package com.team.tracker.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.team.tracker.backend.models.Project;

public interface ProjectRepository extends CrudRepository<Project, String> {
    List<Project> findByOwner(String owner);
}
