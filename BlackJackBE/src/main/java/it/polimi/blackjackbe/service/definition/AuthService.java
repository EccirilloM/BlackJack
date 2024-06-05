package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.LoginRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.LoginResponse;
import lombok.NonNull;

import org.springframework.http.HttpHeaders;

/**
 * Interfaccia che contiene le firme dei metodi del service che gestisce l'autenticazione.
 * Implementazione -> {@link it.polimi.blackjackbe.service.implementation.AuthServiceImplementation}.
 */
public interface AuthService {

    /**
     * Metodo per registrare un utente sul database.
     * @param request DTO con i dati per la registrazione.
     * @throws RuntimeException possibile eccezione causata dal client.
     */
    void registrazionePlayer(@NonNull RegistrazioneRequest request) throws RuntimeException;

    /**
     * Metodo per il login. Dopo aver eseguito i controlli viene generato un JWT per l'utente loggato.
     * @param request DTO con i dati per il login.
     * @return DTO con i dati dell'utente.
     */
    LoginResponse login(@NonNull LoginRequest request);

    /**
     * Crea un oggetto {@link HttpHeaders} e aggiunge il token, così da mandarlo al client.
     * @param jwt Stringa con il token.
     * @return L'oggetto {@link HttpHeaders} con il token al suo interno.
     */
    HttpHeaders putJwtInHttpHeaders(String jwt);
}
