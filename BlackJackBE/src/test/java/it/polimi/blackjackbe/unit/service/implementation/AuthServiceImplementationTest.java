package it.polimi.blackjackbe.unit.service.implementation;

import it.polimi.blackjackbe.dto.request.LoginRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.exception.ConflictException;
import it.polimi.blackjackbe.exception.InternalServerErrorException;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.security.utils.JwtUtils;
import it.polimi.blackjackbe.service.definition.UserService;
import it.polimi.blackjackbe.service.implementation.AuthServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplementationTest {

    @Mock
    UserService userService;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtUtils jwtUtils;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    AuthServiceImplementation authService;

    @Test
    void registrazionePlayerThrowsErroreDuranteLaRegistrazione() {
        RegistrazioneRequest request = new RegistrazioneRequest("nome", "cognome", "username", "email", "password", LocalDateTime.now());
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        assertThrows(InternalServerErrorException.class, () -> authService.registrazionePlayer(request));
    }

    @Test
    void registrazionePlayerThrowsUsernameGiaRegistrato() {
        RegistrazioneRequest request = new RegistrazioneRequest("nome", "cognome", "username", "email", "password", LocalDateTime.now());
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new User()));
        assertThrows(ConflictException.class, () -> authService.registrazionePlayer(request));
    }

    @Test
    void loginSuccessful() {

        LoginRequest request = new LoginRequest("username", "password");

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        assertAll(() -> authService.login(request));
    }

    @Test
    void putJwtInHttpHeadersSuccessful() {

        assertAll(() -> authService.putJwtInHttpHeaders("jwt"));
    }
}
