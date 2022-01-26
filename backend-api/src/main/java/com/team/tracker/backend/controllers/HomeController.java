package com.team.tracker.backend.controllers;

import java.util.HashMap;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public HashMap<String, String> home() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        HashMap<String, String> values = new HashMap<>();
        values.put("hello", "Welcome!");
        return values;
    }
}
