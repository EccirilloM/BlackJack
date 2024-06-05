package it.polimi.blackjackbe.unit.service.implementation;

import it.polimi.blackjackbe.builder.TabacchiBuilder;
import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.dto.request.AdminAggiornaDatiUtenteRequest;
import it.polimi.blackjackbe.dto.request.AggiornaDatiRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.ConflictException;
import it.polimi.blackjackbe.exception.InternalServerErrorException;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.TavoloRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplementationTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    TabacchiRepository tabacchiRepository;
    @Mock
    TavoloRepository tavoloRepository;
    @Mock
    ManoRepository manoRepository;
    @InjectMocks
    UserServiceImplementation userServiceImplementation;

    @Test
    void getUserDataByIdSuccessful() {
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new UserBuilder()
                        .userId(1L)
                        .nome("nome")
                        .cognome("cognome")
                        .email("email")
                        .username("username")
                        .ruolo(Ruolo.ECONOMO)
                        .dataNascita(LocalDateTime.now())
                        .dataRegistrazione(LocalDateTime.now())
                        .saldo(0.0)
                .build()));
        assertAll(() -> userServiceImplementation.getUserDataById(1L));
    }

    @Test
    void getUserDataByIdThrowsUtenteNonValido() {
        when(userRepository.findByUserId(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userServiceImplementation.getUserDataById(1L));
    }

    @Test
    void getUserDataByIdThrowsIdNonValido() {
        assertThrows(BadRequestException.class, () -> userServiceImplementation.getUserDataById(-1L));
    }

    @Test
    void deleteUserThrowsErroreEliminazioneUtente() {
        when(tabacchiRepository.findAll()).thenReturn(List.of(new TabacchiBuilder().economo(new UserBuilder().userId(2L).build()).build()));
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new User()));
        when(tavoloRepository.findAllByPlayer(any(User.class))).thenReturn(Collections.emptyList()); // Aggiungi questa riga
        assertThrows(InternalServerErrorException.class, () -> userServiceImplementation.deleteUser(1L));
    }

    @Test
    void deleteUserThrowsUtenteAssociatoTabacchi() {
        when(tabacchiRepository.findAll()).thenReturn(List.of(new TabacchiBuilder().economo(new UserBuilder().userId(1L).build()).build()));
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new User()));
        assertThrows(ConflictException.class, () -> userServiceImplementation.deleteUser(1L));
    }

    @Test
    void deleteUserThrowsUtenteNonTrovato() {
        when(userRepository.findByUserId(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userServiceImplementation.deleteUser(1L));
    }

    @Test
    void deleteUserThrowsIdNonValido() {
        assertThrows(BadRequestException.class, () -> userServiceImplementation.deleteUser(-1L));
    }

    @Test
    void getAllByRuoloSuccessful() {
        when(userRepository.findAllByRuolo(any())).thenReturn(List.of(new UserBuilder()
                        .userId(1L)
                        .nome("nome")
                        .cognome("cognome")
                        .email("email")
                        .username("username")
                        .ruolo(Ruolo.ECONOMO)
                        .dataNascita(LocalDateTime.now())
                        .dataRegistrazione(LocalDateTime.now())
                        .saldo(0.0)
                .build()));
        assertAll(() -> userServiceImplementation.getAllByRuolo("ECONOMO"));
    }

    @Test
    void getAllByRuoloThrowsUtentiNonTrovati() {
        when(userRepository.findAllByRuolo(any())).thenReturn(List.of());
        assertThrows(NotFoundException.class, () -> userServiceImplementation.getAllByRuolo("PLAYER"));
    }

    @Test
    void getAllByRuoloThrowsUtentiNonTrovatiAdmin() {
        when(userRepository.findAllByRuolo(any())).thenReturn(List.of());
        assertThrows(NotFoundException.class, () -> userServiceImplementation.getAllByRuolo("ADMIN"));
    }

    @Test
    void getAllByRuoloThrowsRuoloNonValido() {
        assertThrows(BadRequestException.class, () -> userServiceImplementation.getAllByRuolo("ruolo"));
    }

    @Test
    void getAllByRuoloThrowsRuoloNonValido2() {
        assertThrows(BadRequestException.class, () -> userServiceImplementation.getAllByRuolo(""));
    }

    @Test
    void ricaricaSaldo() {
        when(userRepository.findAllByRuolo(any())).thenReturn(List.of(new UserBuilder()
                        .username("username")
                        .saldo(0.0)
                .build()));
        assertAll(() -> userServiceImplementation.ricaricaSaldo());
    }

    @Test
    void getAllSuccessful() {
        when(userRepository.findAll()).thenReturn(List.of(new UserBuilder()
                        .userId(1L)
                        .nome("nome")
                        .cognome("cognome")
                        .email("email")
                        .username("username")
                        .ruolo(Ruolo.ECONOMO)
                        .dataNascita(LocalDateTime.now())
                        .dataRegistrazione(LocalDateTime.now())
                        .saldo(0.0)
                .build()));
        assertAll(() -> userServiceImplementation.getAll());
    }

    @Test
    void getAllThrowsUtentiNonTrovati() {
        when(userRepository.findAll()).thenReturn(List.of());
        assertThrows(NotFoundException.class, () -> userServiceImplementation.getAll());
    }

    @Test
    void aggiornaDatiUtenteSuccessful() {
        AggiornaDatiRequest request = new AggiornaDatiRequest("nome", "cognome", "email", "username", "vecchiaPassword", "nuovaPassword");
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new UserBuilder().username("gio").build()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        assertAll(() -> userServiceImplementation.aggiornaDatiUtente(request, 1L));
    }

    @Test
    void aggiornaDatiUtenteThrowsVecchiaPasswordNonCorretta() {
        AggiornaDatiRequest request = new AggiornaDatiRequest("nome", "cognome", "email", "username", "vecchiaPassword", "nuovaPassword");
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new UserBuilder().username("gio").build()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(any(), any())).thenReturn(false);
        assertThrows(BadRequestException.class, () -> userServiceImplementation.aggiornaDatiUtente(request, 1L));
    }

    @Test
    void aggiornaDatiUtenteThrowsNuovaPasswordUgualeVecchiaPassword() {
        AggiornaDatiRequest request = new AggiornaDatiRequest("nome", "cognome", "email", "username", "password", "password");
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new UserBuilder().username("gio").build()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        assertThrows(BadRequestException.class, () -> userServiceImplementation.aggiornaDatiUtente(request, 1L));
    }

    @Test
    void aggiornaDatiUtenteThrowsUsernameGiaInUso() {
        AggiornaDatiRequest request = new AggiornaDatiRequest("nome", "cognome", "email", "username", "password", "password");
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new UserBuilder().username("gio").build()));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        assertThrows(BadRequestException.class, () -> userServiceImplementation.aggiornaDatiUtente(request, 1L));
    }

    @Test
    void aggiornaDatiUtenteThrowsUtenteNonTrovato() {
        AggiornaDatiRequest request = new AggiornaDatiRequest("nome", "cognome", "email", "username", "password", "password");
        when(userRepository.findByUserId(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userServiceImplementation.aggiornaDatiUtente(request, 1L));
    }

    @Test
    void adminAggiornaDatiUtenteSuccessful() {
        AdminAggiornaDatiUtenteRequest request = new AdminAggiornaDatiUtenteRequest("nome", "cognome", "email", "username");
        User user = new UserBuilder()
                .userId(1L)
                .nome("nome")
                .cognome("cognome")
                .email("email")
                .username("usernamefr")
                .ruolo(Ruolo.ECONOMO)
                .dataNascita(LocalDateTime.now())
                .dataRegistrazione(LocalDateTime.now())
                .saldo(0.0)
                .build();
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        assertAll(() -> userServiceImplementation.adminAggiornaDatiUtente(request, 1L));
    }

    @Test
    void adminAggiornaDatiUtenteThrowsUsernameGiaInUso() {
        AdminAggiornaDatiUtenteRequest request = new AdminAggiornaDatiUtenteRequest("nome", "cognome", "email", "username");
        User user = new UserBuilder()
                .userId(1L)
                .nome("nome")
                .cognome("cognome")
                .email("email")
                .username("usernamfe")
                .ruolo(Ruolo.ECONOMO)
                .dataNascita(LocalDateTime.now())
                .dataRegistrazione(LocalDateTime.now())
                .saldo(0.0)
                .build();
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        assertThrows(BadRequestException.class, () -> userServiceImplementation.adminAggiornaDatiUtente(request, 1L));
    }

    @Test
    void adminAggiornaDatiUtenteThrowsUtenteNonTrovato() {
        AdminAggiornaDatiUtenteRequest request = new AdminAggiornaDatiUtenteRequest("nome", "cognome", "email", "username");
        when(userRepository.findByUserId(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userServiceImplementation.adminAggiornaDatiUtente(request, 1L));
    }

    @Test
    void creaEconomoThrowsErroreDuranteLaRegistrazione() {
        RegistrazioneRequest request = new RegistrazioneRequest("nome", "cognome", "email", "username", "password", LocalDateTime.now());
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        assertThrows(InternalServerErrorException.class, () -> userServiceImplementation.creaEconomo(request));
    }

    @Test
    void creaEconomoThrowsUsernameGiaRegistrato() {
        RegistrazioneRequest request = new RegistrazioneRequest("nome", "cognome", "email", "username", "password", LocalDateTime.now());
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        assertThrows(ConflictException.class, () -> userServiceImplementation.creaEconomo(request));
    }
}
