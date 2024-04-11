package it.polimi.blackjackbe.service.implementation;

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

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public void registrazionePlayer(@NonNull RegistrazioneRequest request) throws RuntimeException {

        Optional<User> userAlreadyRegistered = userRepository.findByUsername(request.getUsername());

        if (userAlreadyRegistered.isPresent()) {
            throw new ConflictException("Username già registrato");
        }

        final String nome = request.getNome().trim();
        final String cognome = request.getCognome().trim();
        final String username = request.getUsername().trim().toLowerCase();
        final String email = request.getEmail().trim().toLowerCase();
        final String password = request.getPassword();
        final LocalDateTime dataNascita = request.getDataNascita();
        final Ruolo ruolo = Ruolo.PLAYER;

        checkUserData(List.of(nome, cognome, username, email, password, ruolo.name()));

        User user = new User();
        user.setNome(nome);
        user.setCognome(cognome);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRuolo(ruolo);
        user.setDataNascita(dataNascita);
        user.setDataRegistrazione(LocalDateTime.now());
        user.setSaldo(100.00);

        userRepository.save(user);

        Optional<User> userRegistered = userRepository.findByUsername(username);

        if (userRegistered.isEmpty()) {
            throw new InternalServerErrorException("Errore durante la registrazione");
        }
    }

    @Override
    public LoginResponse login(@NonNull LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername().trim().toLowerCase(),
                        request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        return new LoginResponse(
                user.getUserId(),
                user.getNome(),
                user.getCognome(),
                user.getEmail(),
                user.getUsername(),
                user.getRuolo(),
                "Accesso effettuato con successo",
                jwtUtils.generateToken(user),
                user.getSaldo()
                );
    }

    @Override
    public HttpHeaders putJwtInHttpHeaders(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer: " + jwt);
        return headers;
    }

    private void checkUserData(@NonNull List<String> dataList) throws RuntimeException {
        for (String data : dataList) {
            if (data.isEmpty() || data.isBlank()) {
                throw new BadRequestException("Dati mancanti");
            }
        }
    }
}

