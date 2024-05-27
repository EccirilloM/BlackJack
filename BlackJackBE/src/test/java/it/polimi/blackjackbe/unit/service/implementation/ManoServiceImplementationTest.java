package it.polimi.blackjackbe.unit.service.implementation;

import it.polimi.blackjackbe.builder.ManoBuilder;
import it.polimi.blackjackbe.builder.TavoloBuilder;
import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.ManoServiceImplementation;
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
public class ManoServiceImplementationTest {

    @Mock
    UserRepository userRepository;
    @Mock
    ManoRepository manoRepository;
    @InjectMocks
    ManoServiceImplementation manoService;

    @Test
    void getAllMani() {
        when(manoRepository.findAll()).thenReturn(List.of(new ManoBuilder().manoId(1L).tavolo(new TavoloBuilder().tavoloId(1L).player(new UserBuilder().username("username").build()).build()).dataMano(LocalDateTime.now()).importo(1.0).build()));
        assertAll(() -> manoService.getAllMani());
    }

    @Test
    void getAllManiByUserIdSuccessful() {
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(manoRepository.findAll()).thenReturn(List.of(new ManoBuilder().manoId(1L).tavolo(new TavoloBuilder().tavoloId(1L).player(new UserBuilder().userId(1L).username("username").build()).build()).dataMano(LocalDateTime.now()).importo(1.0).build()));
        assertAll(() -> manoService.getAllManiByUserId(1L));
    }

    @Test
    void getAllManiByUserIdThrowsUtenteNonTrovato() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> manoService.getAllManiByUserId(1L));
    }

    @Test
    void getAllManiByUserIdThrowsUserIdNegativo() {
        assertThrows(IllegalArgumentException.class, () -> manoService.getAllManiByUserId(-1L));
    }
}
