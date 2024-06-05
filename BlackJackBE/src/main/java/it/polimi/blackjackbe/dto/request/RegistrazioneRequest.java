package it.polimi.blackjackbe.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

/**
 * DTO per la richiesta di registrazione di un nuovo utente.
 * Contiene i dati necessari per la registrazione e include le annotazioni di validazione per garantire l'integrità dei dati.
 */

@Getter
@AllArgsConstructor
public class RegistrazioneRequest {

    /**
     * Nome dell'utente.
     * Non deve essere vuoto.
     */
    @NotBlank(message = "Nome non può essere vuoto")
    private String nome;

    /**
     * Cognome dell'utente.
     * Non deve essere vuoto.
     */
    @NotBlank(message = "Cognome non può essere vuoto")
    private String cognome;

    /**
     * Email dell'utente.
     * Non deve essere vuota e deve avere un formato valido.
     */
    @NotBlank(message = "Email non può essere vuota")
    @Email(message = "Email deve avere un formato valido")
    private String email;

    /**
     * Username dell'utente.
     * Non deve essere vuoto.
     */
    @NotBlank(message = "Username non può essere vuoto")
    private String username;

    /**
     * Password dell'utente.
     * Non deve essere vuota e deve essere di almeno 8 caratteri.
     */
    @NotBlank(message = "Password non può essere vuota")
    @Size(min = 8, message = "La password deve essere di almeno 8 caratteri")
    private String password;

    /**
     * Data di nascita dell'utente.
     * Non deve essere vuota e deve essere una data passata.
     */
    @NotNull(message = "Data di nascita non può essere vuota")
    @Past(message = "Data di nascita non può essere futura")
    private LocalDateTime dataNascita;
}
