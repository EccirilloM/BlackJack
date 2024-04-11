package it.polimi.blackjackbe.dto.response;

import it.polimi.blackjackbe.model.Ruolo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private Long userId;

    private String nome;

    private String cognome;

    private String email;

    private String username;

    private Ruolo ruolo;

    private String message;

    private String jwtToken;

    private Double saldo;

    private LocalDateTime dataNascita;

    private LocalDateTime dataRegistrazione;
}
