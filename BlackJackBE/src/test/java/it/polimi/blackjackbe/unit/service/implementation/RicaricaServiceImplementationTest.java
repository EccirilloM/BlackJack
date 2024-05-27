package it.polimi.blackjackbe.unit.service.implementation;

import it.polimi.blackjackbe.builder.RicaricaBuilder;
import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.dto.request.RichiestaRicaricaRequest;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.NotificaRepository;
import it.polimi.blackjackbe.repository.RicaricaRepository;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.RicaricaServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RicaricaServiceImplementationTest {

    @Mock
    UserRepository userRepository;
    @Mock
    TabacchiRepository tabacchiRepository;
    @Mock
    NotificaRepository notificaRepository;
    @Mock
    RicaricaRepository ricaricaRepository;
    @InjectMocks
    RicaricaServiceImplementation ricaricaServiceImplementation;

    @Test
    void richiediRicaricaSuccessful(){
        RichiestaRicaricaRequest request = new RichiestaRicaricaRequest(1.0, 1L);
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new UserBuilder().ruolo(Ruolo.PLAYER).build()));
        when(tabacchiRepository.findById(any())).thenReturn(Optional.of(new Tabacchi()));
        assertAll(() -> ricaricaServiceImplementation.richiediRicarica(1L, request));
    }

    @Test
    void richiediRicaricaThrowsTabacchiNonTrovato() {
        RichiestaRicaricaRequest request = new RichiestaRicaricaRequest(1.0, 1L);
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new UserBuilder().ruolo(Ruolo.PLAYER).build()));
        when(tabacchiRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> ricaricaServiceImplementation.richiediRicarica(1L, request));
    }

    @Test
    void richiediRicaricaThrowsIdNonValido() {
        RichiestaRicaricaRequest request = new RichiestaRicaricaRequest(1.0, -1L);
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new UserBuilder().ruolo(Ruolo.PLAYER).build()));
        assertThrows(BadRequestException.class, () -> ricaricaServiceImplementation.richiediRicarica(1L, request));
    }

    @Test
    void richiediRicaricaThrowsRuoloNonValido() {
        RichiestaRicaricaRequest request = new RichiestaRicaricaRequest(1.0, -1L);
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new UserBuilder().ruolo(Ruolo.ADMIN).build()));
        assertThrows(BadRequestException.class, () -> ricaricaServiceImplementation.richiediRicarica(1L, request));
    }

    @Test
    void richiediRicaricaThrowsUtenteNonTrovato() {
        RichiestaRicaricaRequest request = new RichiestaRicaricaRequest(1.0, -1L);
        when(userRepository.findByUserId(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> ricaricaServiceImplementation.richiediRicarica(1L, request));
    }

    @Test
    void richiediRicaricaThrowsUserIdNonValido() {
        RichiestaRicaricaRequest request = new RichiestaRicaricaRequest(1.0, -1L);
        assertThrows(BadRequestException.class, () -> ricaricaServiceImplementation.richiediRicarica(-1L, request));
    }

    @Test
    void richiediRicaricaThrowsImportoNonValido() {
        RichiestaRicaricaRequest request = new RichiestaRicaricaRequest(-1.0, 1L);
        assertThrows(BadRequestException.class, () -> ricaricaServiceImplementation.richiediRicarica(-1L, request));
    }

    @Test
    void getAllRichiesteRicaricaSuccessful() {
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new UserBuilder().ruolo(Ruolo.ECONOMO).build()));
        when(tabacchiRepository.findAllByEconomo(any())).thenReturn(List.of(new Tabacchi()));
        when(ricaricaRepository.findAllByTabacchi(any())).thenReturn(List.of(new RicaricaBuilder().ricaricaId(1L).player(new UserBuilder().userId(1L).nome("nome").cognome("cognome").email("email").username("username").build()).dataRichiesta(LocalDateTime.now()).importo(1.0).accettata(false).richiesta(true).build()));
        assertAll(() -> ricaricaServiceImplementation.getAllRichiesteRicarica(1L));
    }

    @Test
    void getAllRichiesteRicaricaThrowsRuoloNonValido() {
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(new UserBuilder().ruolo(Ruolo.PLAYER).build()));
        assertThrows(BadRequestException.class, () -> ricaricaServiceImplementation.getAllRichiesteRicarica(1L));
    }

    @Test
    void getAllRichiesteRicaricaThrowsUtenteNonTrovato() {
        when(userRepository.findByUserId(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> ricaricaServiceImplementation.getAllRichiesteRicarica(1L));
    }

    @Test
    void getAllRichiesteRicaricaThrowsIdNonValido() {
        assertThrows(BadRequestException.class, () -> ricaricaServiceImplementation.getAllRichiesteRicarica(-1L));
    }

    @Test
    void accettaRicaricaSuccessful() {
        when(ricaricaRepository.findById(any())).thenReturn(Optional.of(new RicaricaBuilder().importo(1.0).build()));
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserBuilder().saldo(1.0).build()));
        assertAll(() -> ricaricaServiceImplementation.accettaRicarica(1L, 1L));
    }

    @Test
    void accettaRicaricaThrowsPlayerNonTrovato() {
        when(ricaricaRepository.findById(any())).thenReturn(Optional.of(new RicaricaBuilder().importo(1.0).build()));
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> ricaricaServiceImplementation.accettaRicarica(1L, 1L));
    }

    @Test
    void accettaRicaricaThrowsIdPlayerNonValido() {
        when(ricaricaRepository.findById(any())).thenReturn(Optional.of(new RicaricaBuilder().importo(1.0).build()));
        assertThrows(BadRequestException.class, () -> ricaricaServiceImplementation.accettaRicarica(1L, -1L));
    }

    @Test
    void accettaRicaricaThrowsRicaricaNonTrovata() {
        when(ricaricaRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> ricaricaServiceImplementation.accettaRicarica(1L, -1L));
    }

    @Test
    void accettaRicaricaThrowsIdNonValido() {
        assertThrows(BadRequestException.class, () -> ricaricaServiceImplementation.accettaRicarica(-1L, -1L));
    }

    @Test
    void rifiutaRicaricaSuccessful() {
        when(ricaricaRepository.findById(any())).thenReturn(Optional.of(new RicaricaBuilder().importo(1.0).build()));
        assertAll(() -> ricaricaServiceImplementation.rifiutaRicarica(1L));
    }

    @Test
    void rifiutaRicaricaThrowsRicaricaNonTrovata() {
        when(ricaricaRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> ricaricaServiceImplementation.rifiutaRicarica(1L));
    }

    @Test
    void rifiutaRicaricaThrowsIdNonValido() {
        assertThrows(BadRequestException.class, () -> ricaricaServiceImplementation.rifiutaRicarica(-1L));
    }
}
