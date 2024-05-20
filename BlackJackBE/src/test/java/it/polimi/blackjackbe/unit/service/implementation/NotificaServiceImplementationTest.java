package it.polimi.blackjackbe.unit.service.implementation;

import it.polimi.blackjackbe.builder.NotificaBuilder;
import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.model.Notifica;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.NotificaRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.NotificaServiceImplementation;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificaServiceImplementationTest {

    @Mock
    NotificaRepository notificaRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    NotificaServiceImplementation notificaServiceImplementation;

    @Test
    void getAllByUserIdSuccessful(){
        User user = new UserBuilder()
                .userId(1L)
                .build();
        Notifica notifica = new NotificaBuilder()
                .data(LocalDateTime.now())
                .testo("Testo")
                .build();
        // Mock the findById method of userRepository to return the User
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // Mock the findAllByPlayer method of notificaRepository to return the Notifica
        when(notificaRepository.findAllByPlayer(any())).thenReturn(List.of(notifica));

        // Run the test
        assertAll(() -> notificaServiceImplementation.getAllByUserId(1L));
    }

    @Test
    void getAllByUserIdThrowsIdNonValido(){
        assertThrows(BadRequestException.class, () -> notificaServiceImplementation.getAllByUserId(0L));
    }

    @Test
    void getAllByUserIdThrowsUtenteNonTrovato(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> notificaServiceImplementation.getAllByUserId(1L));
    }
}
