package com.team.tracker.backend.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.team.tracker.backend.models.User;
import com.team.tracker.backend.services.UserService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.team.tracker.backend.security.SecurityConstants.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    public static final String REGISTER = "/register";
    public static final String GOOGLE_OAUTH_LOG_IN = "/google-oauth-login";
    public static final String GOOGLE_OAUTH_TOKEN_INFO_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=";

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @RequestMapping(value = REGISTER, method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, Object> registerUser(@RequestBody User user) {
        HashMap<String, Object> response = new HashMap<>();
        Optional<User> existsUser = userService.findByEmail(user.getEmail());
        if (existsUser.isPresent()) {
            response.put("status", "EMAIL_EXISTS");
            response.put("error", "User is already registered!");
            return response;
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userService.save(user);
        response.put("status", "OK");
        response.put("message", "Successfully registered!");
        return response;
    }

    @RequestMapping(value = GOOGLE_OAUTH_LOG_IN, method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, Object> openLogIn(@RequestParam(value = "idToken", defaultValue = "") String idTokenString) {
        HashMap<String, Object> response = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(GOOGLE_OAUTH_TOKEN_INFO_URL + idTokenString,
                String.class);
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        // TODO: database checkup is registered or not
        String login = jsonObject.getString("email");
        if (login != null && login.length() > 0) {
            Claims claims = Jwts.claims().setSubject(login);
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");
            claims.put("roles", roles);
            String token = Jwts.builder().setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET).compact();
            response.put("access_token", TOKEN_PREFIX + token);
            response.put("status", "OK");
            return response;
        }
        response.put("status", "NOT_OK");
        return response;
    }
}
