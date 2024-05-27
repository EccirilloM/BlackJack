package it.polimi.blackjackbe.unit.controller;

import it.polimi.blackjackbe.controller.UserController;
import it.polimi.blackjackbe.service.definition.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;
    @InjectMocks
    UserController userController;

    @Test
    void getUserDataById() {
        assertAll(() -> userController.getUserDataById("1"));
    }

    @Test
    void deleteAccount() {
        assertAll(() -> userController.deleteAccount("1"));
    }

    @Test
    void getAll() {
        assertAll(() -> userController.getAll());
    }

    @Test
    void getAllByRuolo() {
        assertAll(() -> userController.getAllByRuolo("ruolo"));
    }

    @Test
    void aggiornaDatiUtente() {
        assertAll(() -> userController.aggiornaDatiUtente(null, "1"));
    }

    @Test
    void adminAggiornaDatiUtente() {
        assertAll(() -> userController.adminAggiornaDatiUtente(null, "1"));
    }

    @Test
    void creaEconomo() {
        assertAll(() -> userController.creaEconomo(null));
    }
}
