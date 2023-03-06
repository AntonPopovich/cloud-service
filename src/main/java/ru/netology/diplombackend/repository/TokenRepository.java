package ru.netology.diplombackend.repository;

import org.springframework.data.repository.CrudRepository;
import ru.netology.diplombackend.entity.Token;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, String> {
    Optional<Token> findByToken(String s);

    void deleteByToken(String token);
}
