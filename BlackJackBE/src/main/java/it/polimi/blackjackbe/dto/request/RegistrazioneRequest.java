package it.polimi.blackjackbe.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistrazioneRequest {
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String password;
    private String passwordRipetuta;
    private LocalDateTime dataNascita;
}
