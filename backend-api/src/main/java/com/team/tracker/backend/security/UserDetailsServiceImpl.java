package com.team.tracker.backend.security;

import java.util.Collections;

import com.team.tracker.backend.models.User;
import com.team.tracker.backend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findByEmail(email).orElseThrow();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.emptyList());
    }
}
