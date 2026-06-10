package com.nexcart.controller;

import com.nexcart.dto.auth.AuthResponse;

import com.nexcart.dto.auth.LoginRequest;
import com.nexcart.dto.auth.RegisterRequest;
import com.nexcart.dto.auth.RegisterResponse;
import com.nexcart.entity.User;
import com.nexcart.security.CustomUserDetailsService;
import com.nexcart.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest registerRequest) {

        User user = new User(
                registerRequest.username(),
                registerRequest.email(),
                registerRequest.password()
        );
        customUserDetailsService.registerUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new RegisterResponse(
                                "User registered successfully"
                        )
                );
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

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

        return ResponseEntity.ok(
                new AuthResponse(token)
        );
    }
}
