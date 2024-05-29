package org.example.buchibackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.buchibackend.domain.User;
import org.example.buchibackend.dto.AuthenticationRequest;
import org.example.buchibackend.dto.AuthenticationResponse;
import org.example.buchibackend.dto.RegisterRequest;
import org.example.buchibackend.exception.WrongLoginCredentialsException;
import org.example.buchibackend.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) throws WrongLoginCredentialsException {
        if (authenticationRequest.password() == null || authenticationRequest.email() == null) {
            throw new WrongLoginCredentialsException();
        }
        return authService.login(authenticationRequest);
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest.getUserEntity());
    }
}
