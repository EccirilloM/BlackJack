package it.polimi.blackjackbe.unit.service.implementation;

import it.polimi.blackjackbe.builder.TabacchiBuilder;
import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.dto.request.CreaTabacchiRequest;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.TabacchiServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TabacchiServiceImplementationTest {

    @Mock
    TabacchiRepository tabacchiRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    TabacchiServiceImplementation tabacchiServiceImplementation;

    @Test
    void creaTabacchiSuccessful() {
        CreaTabacchiRequest request = new CreaTabacchiRequest("Tabacchi", 45f, 9f, 1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        assertAll(() -> tabacchiServiceImplementation.creaTabacchi(request));
    }

    @Test
    void creaTabacchiThrowsEconomoNonTrovato() {
        CreaTabacchiRequest request = new CreaTabacchiRequest("nome", 45f, 9f, 1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> tabacchiServiceImplementation.creaTabacchi(request));
    }

    @Test
    void creaTabacchiThrowsIdEconomoNonValido() {
        CreaTabacchiRequest request = new CreaTabacchiRequest("nome", 45f, 9f, -1L);
        assertThrows(IllegalArgumentException.class, () -> tabacchiServiceImplementation.creaTabacchi(request));
    }

    @Test
    void creaTabacchiThrowsLongitudineNonValida() {
        CreaTabacchiRequest request = new CreaTabacchiRequest("nome", 45f, Float.POSITIVE_INFINITY, 1L);
        assertThrows(IllegalArgumentException.class, () -> tabacchiServiceImplementation.creaTabacchi(request));
    }

    @Test
    void creaTabacchiThrowsLatitudineNonValida() {
        CreaTabacchiRequest request = new CreaTabacchiRequest("nome", Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, 1L);
        assertThrows(IllegalArgumentException.class, () -> tabacchiServiceImplementation.creaTabacchi(request));
    }

    @Test
    void creaTabacchiThrowsNomeTabacchiNonValido() {
        CreaTabacchiRequest request = new CreaTabacchiRequest("", Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, 1L);
        assertThrows(IllegalArgumentException.class, () -> tabacchiServiceImplementation.creaTabacchi(request));
    }

    @Test
    void getAllTabacchi() {
        when(tabacchiRepository.findAll()).thenReturn(List.of(new TabacchiBuilder().tabacchiId(1L).nome("nome").lat(1.0).lng(1.0).economo(new UserBuilder().userId(1L).build()).build()));
        assertAll(() -> tabacchiServiceImplementation.getAllTabacchi());
    }

    @Test
    void deleteTabacchiSuccessful() {
        when(tabacchiRepository.findById(1L)).thenReturn(Optional.of(new Tabacchi()));
        assertAll(() -> tabacchiServiceImplementation.deleteTabacchi(1L));
    }

    @Test
    void deleteTabacchiThrowsTabacchiNonTrovato() {
        when(tabacchiRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> tabacchiServiceImplementation.deleteTabacchi(1L));
    }
}
