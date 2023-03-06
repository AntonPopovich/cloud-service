package ru.netology.diplombackend.repository;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.netology.diplombackend.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void findByLogin() {
        User user = new User("user@mail.ru", "123456");
        underTest.save(user);
        Optional<User> expected = underTest.findByLogin("user@mail.ru");
        AssertionsForClassTypes.assertThat(expected).isPresent();

    }
}