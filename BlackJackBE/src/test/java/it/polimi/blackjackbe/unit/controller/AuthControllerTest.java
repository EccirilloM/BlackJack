package it.polimi.blackjackbe.unit.controller;

import it.polimi.blackjackbe.controller.AuthController;
import it.polimi.blackjackbe.dto.request.LoginRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.LoginResponse;
import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.service.definition.AuthService;
import it.polimi.blackjackbe.service.implementation.AuthServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    AuthServiceImplementation authServiceImplementation;

    @InjectMocks
    AuthController authController;

    @Test
    void registrazionePlayer() {
        RegistrazioneRequest request = new RegistrazioneRequest(
                "Nome",
                "Cognome",
                "Email@gmail.com",
                "Username",
                "12345678",
                LocalDateTime.now().minusHours(1));
        assertAll(() -> authController.registrazionePlayer(request));;
    }

    @Test
    void login(){
        LoginRequest request = new LoginRequest(
                "Username",
                "12345678"
        );

        when(authServiceImplementation.login(request)).thenReturn(new LoginResponse(
                1L,"", "", "", "", Ruolo.PLAYER, "", "", 0.0, LocalDateTime.now().minusHours(1), LocalDateTime.now()
        ));

        assertAll(() -> authController.login(request));
    }
}
