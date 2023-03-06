package ru.netology.diplombackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tokens")
@NoArgsConstructor
public class Token {

    @Id
    private String token;

    public Token(String token) {
        this.token = token;
    }
}
