package com.team.tracker.backend.services;

import java.util.List;
import java.util.Optional;

import com.team.tracker.backend.models.Task;

public interface TaskService {
    void save(Task task);

    Optional<Task> findById(String id);

    List<Task> findByProjectId(String projectId);
}
