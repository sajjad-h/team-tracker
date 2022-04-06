package com.team.tracker.backend.controllers;

import java.util.HashMap;
import java.util.List;

import com.team.tracker.backend.models.Project;
import com.team.tracker.backend.services.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    
    @PostMapping("")
    public HashMap<String, Object> createProject(@RequestBody Project project) {
        projectService.save(project);
        HashMap<String, Object> values = new HashMap<>();
        values.put("status", "Ok");
        return values;
    }

    @GetMapping("/owner/{owner}")
    public HashMap<String, Object> getProjectsOfOwner(@PathVariable String owner) {
        List<Project> projectList = projectService.findByOwner(owner);
        HashMap<String, Object> values = new HashMap<>();
        values.put("status", "Ok");
        values.put("data", projectList);
        return values;
    }
}
