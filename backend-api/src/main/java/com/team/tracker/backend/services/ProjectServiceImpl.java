package com.team.tracker.backend.services;

import java.util.List;
import java.util.Optional;

import com.team.tracker.backend.models.Project;
import com.team.tracker.backend.repositories.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public Optional<Project> findById(String projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public List<Project> findByOwner(String owner) {
        return projectRepository.findByOwner(owner);
    }
}
