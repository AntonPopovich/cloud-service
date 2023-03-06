package ru.netology.diplombackend.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.diplombackend.model.AuthenticationRequest;
import ru.netology.diplombackend.model.AuthenticationResponse;
import ru.netology.diplombackend.model.RegisterRequest;
import ru.netology.diplombackend.service.AuthenticationService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authenticationService.register(request);
        return ResponseEntity.ok("Registration succesfull");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader("auth-token") @NotBlank String token
    ) {
        authenticationService.logout(token);
        return ResponseEntity.ok("Successfully logout");
    }
}
