package it.polimi.blackjackbe.dto.response;

import it.polimi.blackjackbe.model.Ruolo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long userId;

    private String nome;

    private String cognome;

    private String email;

    private String username;

    private Ruolo ruolo;

    private LocalDateTime dataNascita;

    private LocalDateTime dataRegistrazione;

    private Double saldo;
}
