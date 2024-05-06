package it.polimi.blackjackbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminAggiornaDatiUtenteRequest {
    private String nome;
    private String cognome;
    private String email;
    private String username;
}
