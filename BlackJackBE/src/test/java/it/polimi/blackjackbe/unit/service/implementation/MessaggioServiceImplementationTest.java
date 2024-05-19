package it.polimi.blackjackbe.unit.service.implementation;

import it.polimi.blackjackbe.builder.MessaggioBuilder;
import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.dto.request.MessaggioRequest;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.Messaggio;
import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.repository.MessaggioRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.MessaggioServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessaggioServiceImplementationTest {

    @Mock
    UserRepository userRepository;

    @Mock
    MessaggioRepository messaggioRepository;

    @InjectMocks
    MessaggioServiceImplementation messaggioServiceImplementation;

    @Test
    void getAllMessageByTipoTavoloSuccessfulBase(){
        Messaggio messaggio = new MessaggioBuilder()
                .testo("Testo")
                .createdAt(LocalDateTime.now())
                .user(new UserBuilder()
                        .username("Username")
                        .ruolo(Ruolo.ECONOMO)
                        .build())
                .build();
        when(messaggioRepository.findAllByTipoTavolo(TipoTavolo.BASE)).thenReturn(List.of(messaggio));
        assertAll(() -> messaggioServiceImplementation.getAllMessagesByTipoTavolo("BASE"));
    }

    @Test
    void getAllMessageByTipoTavoloSuccessfulPremium(){
        Messaggio messaggio = new MessaggioBuilder()
                .testo("Testo")
                .createdAt(LocalDateTime.now())
                .user(new UserBuilder()
                        .username("Username")
                        .ruolo(Ruolo.ECONOMO)
                        .build())
                .build();
        when(messaggioRepository.findAllByTipoTavolo(TipoTavolo.PREMIUM)).thenReturn(List.of(messaggio));
        assertAll(() -> messaggioServiceImplementation.getAllMessagesByTipoTavolo("PREMIUM"));
    }

    @Test
    void getAllMessageByTipoTavoloSuccessfulVip(){
        Messaggio messaggio = new MessaggioBuilder()
                .testo("Testo")
                .createdAt(LocalDateTime.now())
                .user(new UserBuilder()
                        .username("Username")
                        .ruolo(Ruolo.ECONOMO)
                        .build())
                .build();
        when(messaggioRepository.findAllByTipoTavolo(TipoTavolo.VIP)).thenReturn(List.of(messaggio));
        assertAll(() -> messaggioServiceImplementation.getAllMessagesByTipoTavolo("VIP"));
    }

    @Test
    void getAllMessageByTipoTavoloSuccessfulExclusive(){
        Messaggio messaggio = new MessaggioBuilder()
                .testo("Testo")
                .createdAt(LocalDateTime.now())
                .user(new UserBuilder()
                        .username("Username")
                        .ruolo(Ruolo.ECONOMO)
                        .build())
                .build();
        when(messaggioRepository.findAllByTipoTavolo(TipoTavolo.EXCLUSIVE)).thenReturn(List.of(messaggio));
        assertAll(() -> messaggioServiceImplementation.getAllMessagesByTipoTavolo("EXCLUSIVE"));
    }


    @Test
    void getAllMessageByTipoTavoloThrowsTipoTavoloNonValido(){
        assertThrows(BadRequestException.class, () -> messaggioServiceImplementation.getAllMessagesByTipoTavolo("INVALIDO"));
    }

    @Test
    void inviaMessaggioSuccesfulBase(){
        MessaggioRequest messaggioRequest = new MessaggioRequest("Ciao", 1L,"BASE");
        when(userRepository.findByUserId(1L)).thenReturn(java.util.Optional.of(new UserBuilder().build()));
        assertAll(() -> messaggioServiceImplementation.inviaMessaggio(messaggioRequest));
    }

    @Test
    void inviaMessaggioSuccesfulPremium(){
        MessaggioRequest messaggioRequest = new MessaggioRequest("Ciao", 1L,"PREMIUM");
        when(userRepository.findByUserId(1L)).thenReturn(java.util.Optional.of(new UserBuilder().build()));
        assertAll(() -> messaggioServiceImplementation.inviaMessaggio(messaggioRequest));
    }

    @Test
    void inviaMessaggioSuccesfulVip(){
        MessaggioRequest messaggioRequest = new MessaggioRequest("Ciao", 1L,"VIP");
        when(userRepository.findByUserId(1L)).thenReturn(java.util.Optional.of(new UserBuilder().build()));
        assertAll(() -> messaggioServiceImplementation.inviaMessaggio(messaggioRequest));
    }

    @Test
    void inviaMessaggioSuccesfulExclusive(){
        MessaggioRequest messaggioRequest = new MessaggioRequest("Ciao", 1L,"EXCLUSIVE");
        when(userRepository.findByUserId(1L)).thenReturn(java.util.Optional.of(new UserBuilder().build()));
        assertAll(() -> messaggioServiceImplementation.inviaMessaggio(messaggioRequest));
    }

    @Test
    void inviaMessaggioThrowsUtenteNonTrovato(){
        MessaggioRequest messaggioRequest = new MessaggioRequest("Ciao", 1L,"BASE");
        when(userRepository.findByUserId(1L)).thenReturn(java.util.Optional.empty());
        assertThrows(NotFoundException.class, () -> messaggioServiceImplementation.inviaMessaggio(messaggioRequest));
    }

    @Test
    void inviaMessaggioThrowsTipoTavoloNonValido(){
        MessaggioRequest messaggioRequest = new MessaggioRequest("Ciao", 1L,"INVALIDO");
        assertThrows(BadRequestException.class, () -> messaggioServiceImplementation.inviaMessaggio(messaggioRequest));
    }

    @Test
    void inviaMessaggioThrowsIdUtenteNonValido(){
        MessaggioRequest messaggioRequest = new MessaggioRequest("Ciao", null,"INVALIDO");
        assertThrows(IllegalArgumentException.class, () -> messaggioServiceImplementation.inviaMessaggio(messaggioRequest));
    }

    @Test
    void inviaMessaggioThrowsTestoMessaggioNonValido(){
        MessaggioRequest messaggioRequest = new MessaggioRequest("", 1L,"BASE");
        assertThrows(IllegalArgumentException.class, () -> messaggioServiceImplementation.inviaMessaggio(messaggioRequest));
    }
}
