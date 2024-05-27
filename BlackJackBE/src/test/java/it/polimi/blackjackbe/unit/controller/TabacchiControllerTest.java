package it.polimi.blackjackbe.unit.controller;

import it.polimi.blackjackbe.controller.TabacchiController;
import it.polimi.blackjackbe.service.definition.TabacchiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class TabacchiControllerTest {

    @Mock
    TabacchiService tabacchiService;
    @InjectMocks
    TabacchiController tabacchiController;

    @Test
    void creaTabacchi() {
        assertAll(() -> tabacchiController.creaTabacchi(null));
    }

    @Test
    void getAllTabacchi() {
        assertAll(() -> tabacchiController.getAllTabacchi());
    }

    @Test
    void deleteTabacchi() {
        assertAll(() -> tabacchiController.deleteTabacchi("1"));
    }
}
