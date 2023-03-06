package ru.netology.diplombackend.repository;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.netology.diplombackend.entity.FileEntity;
import ru.netology.diplombackend.entity.Token;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TokenRepositoryTest {

    @Autowired
    private TokenRepository underTest;

    @Test
    void findByToken() {
        Token token = new Token("123456789qwerty");
        underTest.save(token);
        Optional<Token> expected = underTest.findByToken("123456789qwerty");
        AssertionsForClassTypes.assertThat(expected).isPresent();

    }

    @Test
    void deleteByToken() {
        Token token = new Token("123456789qwerty");
        underTest.deleteByToken(token.getToken());
        Optional<Token> deleteToken = underTest.findByToken("123456789qwerty");
        AssertionsForClassTypes.assertThat(deleteToken).isEmpty();
    }
}