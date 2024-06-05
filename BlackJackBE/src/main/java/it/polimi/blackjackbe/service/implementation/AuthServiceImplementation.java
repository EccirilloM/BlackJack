package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.dto.request.LoginRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.LoginResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.ConflictException;
import it.polimi.blackjackbe.exception.InternalServerErrorException;
import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.security.utils.JwtUtils;
import it.polimi.blackjackbe.service.definition.AuthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service per gestire tutti i metodi inerenti all'autenticazione.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    /**
     * Metodo per registrare un utente sul database.
     * @param request DTO con i dati per la registrazione.
     * @throws RuntimeException possibile eccezione causata dal client.
     */
    @Override
    public void registrazionePlayer(@NonNull RegistrazioneRequest request) throws RuntimeException {

        // Controlla se esiste già un utente con questo username sul database.
        Optional<User> userAlreadyRegistered = userRepository.findByUsername(request.getUsername());

        // Se esiste, lancia un'eccezione.
        if (userAlreadyRegistered.isPresent()) {
            throw new ConflictException("Username già registrato");
        }

        // Assegna a delle variabili i dati della richiesta.
        final String nome = request.getNome().trim();
        final String cognome = request.getCognome().trim();
        final String username = request.getUsername().trim().toLowerCase();
        final String email = request.getEmail().trim().toLowerCase();
        final String password = request.getPassword();
        final LocalDateTime dataNascita = request.getDataNascita();
        final Ruolo ruolo = Ruolo.PLAYER;

        // Controlla che tutti i campi non siano vuoti.
        checkUserData(List.of(nome, cognome, username, email, password));

        // Costruisce l'oggetto utente con il pattern builder.
        User user = new UserBuilder()
                .nome(nome)
                .cognome(cognome)
                .username(username)
                .email(email)
                // Password codificata.
                .password(passwordEncoder.encode(password))
                .ruolo(ruolo)
                .dataNascita(dataNascita)
                .dataRegistrazione(LocalDateTime.now())
                .saldo(100.00)
                .build();

        // Salva l'utente sul database.
        userRepository.save(user);

        // Controlla se l'utente è presente sul database, se sì la registrazione è andata a buon fine.
        Optional<User> userRegistered = userRepository.findByUsername(username);

        // Se l'utente appena registrato non è presente sul database, lancia un'eccezione.
        if (userRegistered.isEmpty()) {
            throw new InternalServerErrorException("Errore durante la registrazione");
        }
    }

    /**
     * Metodo per il login. Dopo aver eseguito i controlli viene generato un JWT per l'utente loggato.
     * @param request DTO con i dati per il login.
     * @return DTO con i dati dell'utente.
     */
    @Override
    public LoginResponse login(@NonNull LoginRequest request) {

        // Autentica l'utente con le credenziali fornite.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername().trim().toLowerCase(),
                        request.getPassword()));

        // Recupera l'utente dal database.
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        // Ritorna il DTO con i dati dell'utente e il token JWT.
        return new LoginResponse(
                user.getUserId(),
                user.getNome(),
                user.getCognome(),
                user.getEmail(),
                user.getUsername(),
                user.getRuolo(),
                "Accesso effettuato con successo",
                jwtUtils.generateToken(user),
                user.getSaldo(),
                user.getDataNascita(),
                user.getDataRegistrazione()
        );
    }

    /**
     * Crea un oggetto {@link HttpHeaders} e aggiunge il token, così da mandarlo al client.
     * @param jwt Stringa con il token.
     * @return L'oggetto {@link HttpHeaders} con il token al suo interno.
     */
    @Override
    public HttpHeaders putJwtInHttpHeaders(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer: " + jwt);
        return headers;
    }

    /**
     * Controlla se tutti i campi sono stati compilati.
     * @param dataList Lista di stringhe da controllare.
     * @throws RuntimeException Eccezione causata da un campo vuoto.
     */
    private void checkUserData(@NonNull List<String> dataList) throws RuntimeException {
        for (String data : dataList) {
            if (data.isEmpty() || data.isBlank()) {
                throw new BadRequestException("Dati mancanti");
            }
        }
    }
}

