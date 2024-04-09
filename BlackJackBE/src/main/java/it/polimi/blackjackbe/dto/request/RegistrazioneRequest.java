package it.polimi.blackjackbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RegistrazioneRequest {
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String password;
    private String passwordRipetuta;
    private LocalDateTime dataNascita;
    private String ruolo;
}
