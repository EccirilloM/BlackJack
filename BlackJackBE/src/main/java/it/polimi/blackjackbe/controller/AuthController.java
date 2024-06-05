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

/**
 * Controller per la gestione dell'autenticazione.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint per la registrazione di un nuovo giocatore.
     * Chiama il servizio di autenticazione per registrare il giocatore nel sistema.
     * @param request DTO con i dati per la registrazione.
     * @return Messaggio di avvenuta registrazione.
     */
    @PostMapping("/registrazionePlayer")
    public ResponseEntity<MessageResponse> registrazionePlayer(@Valid @RequestBody RegistrazioneRequest request) {
        // Chiama il servizio di autenticazione per registrare un nuovo giocatore
        authService.registrazionePlayer(request);

        // Restituisce una risposta di successo con lo stato HTTP 201 Created
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse("Registrazione effettuata con successo"));
    }

    /**
     * Endpoint per il login.
     * Chiama il servizio di autenticazione per autenticare l'utente e ottenere un token JWT.
     * @param request DTO con i dati per il login.
     * @return DTO con l'utente autenticato e un messaggio di avvenuta autenticazione.
     * Nell'header la stringa jwt.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        // Chiama il servizio di autenticazione per autenticare l'utente
        final LoginResponse response = authService.login(request);

        // Restituisce una risposta di successo con lo stato HTTP 200 OK e l'header JWT
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(authService.putJwtInHttpHeaders(response.getJwtToken()))
                .body(response);
    }
}




