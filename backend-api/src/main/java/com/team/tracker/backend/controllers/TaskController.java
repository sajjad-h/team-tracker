package com.team.tracker.backend.controllers;

import java.util.HashMap;
import java.util.List;

import com.team.tracker.backend.models.Task;
import com.team.tracker.backend.services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    
    @PostMapping("")
    public HashMap<String, Object> createTask(@RequestBody Task task) {
        taskService.save(task);
        HashMap<String, Object> values = new HashMap<>();
        values.put("status", "Ok");
        return values;
    }

    @GetMapping("/projects/{projectId}")
    public HashMap<String, Object> getTasks(@PathVariable String projectId) {
        List<Task> taskList = taskService.findByProjectId(projectId);
        HashMap<String, Object> values = new HashMap<>();
        values.put("status", "Ok");
        values.put("data", taskList);
        return values;
    }
}
