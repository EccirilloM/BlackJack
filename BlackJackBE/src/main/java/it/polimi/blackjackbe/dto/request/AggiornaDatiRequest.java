package it.polimi.blackjackbe.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AggiornaDatiRequest {
    private String nome;
    private String cognome;
    @Email(message = "Email deve avere un formato valido")
    private String email;
    private String username;
    private String vecchiaPassword;
    private String nuovaPassword;
}
