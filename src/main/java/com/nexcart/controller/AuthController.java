package com.nexcart.controller;

import com.nexcart.dto.auth.AuthResponse;

import com.nexcart.dto.auth.LoginRequest;
import com.nexcart.entity.User;
import com.nexcart.security.CustomUserDetailsService;
import com.nexcart.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/register")
    public void registerUser(@RequestBody User user) {
        customUserDetailsService.registerUser(user);
    }

    @PostMapping("/auth/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails user =
                customUserDetailsService.loadUserByUsername(
                        request.getUsername()
                );

        String token =
                jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}
