package it.polimi.blackjackbe.unit.service.implementation;

import it.polimi.blackjackbe.builder.TavoloBuilder;
import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.dto.request.EndTavoloRequest;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.TavoloRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import it.polimi.blackjackbe.singleton.SingletonTavoli;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TavoloServiceImplementationTest {

    @Mock
    TavoloRepository tavoloRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ManoRepository manoRepository;
    @Mock
    Tavolo tavolo;
    @InjectMocks
    TavoloServiceImplementation tavoloServiceImplementation;

    @Test
    void initThrowsUserNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> tavoloServiceImplementation.init("tipoTavolo", 1L));
    }

    @Test
    void initThrowsTableTypeIsRequired() {
        assertThrows(BadRequestException.class, () -> tavoloServiceImplementation.init("", 1L));
    }

    @Test
    void initThrowsUserIdIsRequired() {
        assertThrows(BadRequestException.class, () -> tavoloServiceImplementation.init("", -1L));
    }

    @Test
    void endThrowsTableNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(tavoloRepository.findByPlayer(any())).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> tavoloServiceImplementation.end(1L, new EndTavoloRequest(1.0)));
    }

    @Test
    void endThrowsUserNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> tavoloServiceImplementation.end(1L, new EndTavoloRequest(1.0)));
    }
}
