package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.request.LoginRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.LoginResponse;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.service.definition.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registrazionePlayer")
    public ResponseEntity<MessageResponse> registrazionePlayer(@Valid @RequestBody RegistrazioneRequest request) {
        authService.registrazionePlayer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse("Registrazione effettuata con successo"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        final LoginResponse response = authService.login(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(authService.putJwtInHttpHeaders(response.getJwtToken()))
                .body(response);
    }
}




