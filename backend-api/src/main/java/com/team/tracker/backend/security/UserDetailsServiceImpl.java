package com.team.tracker.backend.security;

import java.util.Collections;
import java.util.Optional;

import com.team.tracker.backend.models.User;
import com.team.tracker.backend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                    user.get().getPassword(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
