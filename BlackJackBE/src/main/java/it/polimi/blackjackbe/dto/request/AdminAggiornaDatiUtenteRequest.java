package it.polimi.blackjackbe.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminAggiornaDatiUtenteRequest {
    private String nome;
    private String cognome;
    @Email(message = "Email deve avere un formato valido")
    private String email;
    private String username;
}
