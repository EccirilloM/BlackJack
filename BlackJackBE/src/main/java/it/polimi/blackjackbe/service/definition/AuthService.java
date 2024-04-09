package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.LoginRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.LoginResponse;
import lombok.NonNull;

import org.springframework.http.HttpHeaders;

public interface AuthService {
    void registrazionePlayer(@NonNull RegistrazioneRequest request) throws RuntimeException;

    LoginResponse login(@NonNull LoginRequest request);

    HttpHeaders putJwtInHttpHeaders(String jwt);

}
