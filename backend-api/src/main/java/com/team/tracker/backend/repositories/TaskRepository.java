package com.team.tracker.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.team.tracker.backend.models.Task;

public interface TaskRepository extends CrudRepository<Task, String> {
    List<Task> findByProjectId(String projectId);
}
