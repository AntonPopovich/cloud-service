package ru.netology.diplombackend.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.netology.diplombackend.entity.Token;
import ru.netology.diplombackend.entity.User;
import ru.netology.diplombackend.model.*;
import ru.netology.diplombackend.repository.TokenRepository;
import ru.netology.diplombackend.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public void register(RegisterRequest request) {
        var user = new User(request.getLogin(), passwordEncoder.encode(request.getPassword()));
        repository.save(user);
    }

    public String initToken(AuthenticationRequest request) {

        var user = repository.findByLogin(request.getLogin())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        tokenRepository.save(new Token(jwtToken));

        return jwtToken;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        var jwtToken = tokenRepository.findByToken(initToken(request));

        return AuthenticationResponse.builder()
                .authToken(jwtToken.toString())
                .build();
    }

    public void logout(String token) {
        tokenRepository.deleteByToken(extractToken(token));
    }

    public boolean isTokenExist(@NonNull String token) {
        return tokenRepository.existsById(extractToken(token));
    }

    private String extractToken(String token) {
        String authToken = token.split(" ")[1].trim();
        return authToken.substring(authToken.indexOf('=') + 1, authToken.indexOf(')'));
    }
}


