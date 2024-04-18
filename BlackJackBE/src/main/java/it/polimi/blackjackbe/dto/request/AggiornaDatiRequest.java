package it.polimi.blackjackbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AggiornaDatiRequest {
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String vecchiaPassword;
    private String nuovaPassword;
}