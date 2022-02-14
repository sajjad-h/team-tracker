package com.team.tracker.backend.services;

import java.util.List;
import java.util.Optional;

import com.team.tracker.backend.models.Task;
import com.team.tracker.backend.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Optional<Task> findById(String taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> findByProjectId(String projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}
