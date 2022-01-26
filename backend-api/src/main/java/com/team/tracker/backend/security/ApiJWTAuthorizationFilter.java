package com.team.tracker.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team.tracker.backend.models.UserRole;

import java.io.IOException;
import java.util.ArrayList;

import static com.team.tracker.backend.security.SecurityConstants.*;

public class ApiJWTAuthorizationFilter extends BasicAuthenticationFilter {
    public ApiJWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            // Extract the UserName
            String user = claims.getSubject();
            // Then convert Roles to GrantedAuthority Object for injecting
            ArrayList<GrantedAuthority> list = new ArrayList<>();
            GrantedAuthority g = new SimpleGrantedAuthority(UserRole.ROLE_USER.toString());
            list.add(g);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, list);
            }
            return null;
        }
        return null;
    }
}
