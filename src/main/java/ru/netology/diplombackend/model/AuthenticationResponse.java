package ru.netology.diplombackend.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AuthenticationResponse {

    private String authToken;

    @JsonGetter("auth-token")
    public String getAuthToken() {
        return authToken;
    }
}
