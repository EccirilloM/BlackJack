package it.polimi.blackjackbe.unit.config;

import it.polimi.blackjackbe.config.CorsConfig;
import it.polimi.blackjackbe.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class CorsConfigTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    CorsConfig corsConfig;

    @Test
    void corsFilterSuccessful() {

        assertAll(() -> corsConfig.corsFilter());
    }

    @Test
    void simpleCorsFilterSuccessful() {

        assertAll(() -> corsConfig.simpleCorsFilter());
    }
}
