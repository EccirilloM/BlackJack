package it.polimi.blackjackbe.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RegistrazioneRequest {
    @NotBlank(message = "Nome non può essere vuoto")
    private String nome;
    @NotBlank(message = "Cognome non può essere vuoto")
    private String cognome;
    @NotBlank(message = "Email non può essere vuota")
    @Email(message = "Email deve avere un formato valido")
    private String email;
    @NotBlank(message = "Username non può essere vuoto")
    private String username;
    @NotBlank(message = "Password non può essere vuota")
    @Size(min = 8, message = "La password deve essere di almeno 8 caratteri")
    private String password;
    @NotNull(message = "Data di nascita non può essere vuota")
    @Past(message = "Data di nascita non può essere futura")
    private LocalDateTime dataNascita;
}
