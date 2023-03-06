package ru.netology.diplombackend.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.netology.diplombackend.entity.Token;
import ru.netology.diplombackend.entity.User;
import ru.netology.diplombackend.model.AuthenticationRequest;
import ru.netology.diplombackend.repository.TokenRepository;
import ru.netology.diplombackend.repository.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenRepository tokenRepository;

    private AuthenticationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AuthenticationService(
                userRepository, passwordEncoder, jwtService, authenticationManager, tokenRepository);
    }

    @Test
    void shouldRegisterUser() {
        User user = new User("test@mail.ru", "123456");
        userRepository.save(user);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User captureUser = userArgumentCaptor.getValue();
        AssertionsForClassTypes.assertThat(captureUser).isEqualTo(user);

    }

    @Test
    void initToken() {
        AuthenticationRequest request = new AuthenticationRequest("test@mail.ru", "123456");
        User users = new User("test@mail.ru", "123456");
        Token token = new Token("123456qwerty");
        userRepository.save(users);

        when(userRepository.findByLogin(request.getLogin())).thenReturn(Optional.of(users));
        Optional<User> userOptional = userRepository.findByLogin(request.getLogin());
        User user = userOptional.get();
        when(jwtService.generateToken(user)).thenReturn("123456qwerty");

        tokenRepository.save(token);
        verify(tokenRepository).save(new Token("123456qwerty"));

        Assertions.assertNotNull(underTest.initToken(request));
        Assertions.assertEquals("123456qwerty", underTest.initToken(request));
    }

    @Test
    void logout() {
        String token = "Bearer Token(token=123456qwerty)";
        underTest.logout(token);
        verify(tokenRepository).deleteByToken(extractToken(token));

    }

    @Test
    @Disabled
    void isTokenExist() {
        String token = "Bearer Token(token=123456qwerty)";
        underTest.isTokenExist(token);
        verify(tokenRepository).existsById(extractToken(token));
    }

    private String extractToken(String token) {
        String authToken = token.split(" ")[1].trim();
        return authToken.substring(authToken.indexOf('=') + 1, authToken.indexOf(')'));
    }
}