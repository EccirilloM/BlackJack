package it.polimi.blackjackbe.unit.config;

import it.polimi.blackjackbe.config.ApplicationConfig;
import it.polimi.blackjackbe.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationConfigTest {

    @Mock
    AuthenticationConfiguration config;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ApplicationConfig applicationConfig;

    @Test
    void userDetailsServiceThrowsUsernameNotFoundException() {
        // Configura userRepository per restituire un Optional vuoto
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Aspettati che venga lanciata un'eccezione UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class, () -> applicationConfig.userDetailsService().loadUserByUsername("username"));
    }

    @Test
    void authenticationProviderSuccessful(){
        assertAll(() -> applicationConfig.authenticationProvider());
    }

    @Test
    void authenticationManagerSuccessful(){
        assertAll(() -> applicationConfig.authenticationManager(config));
    }

    @Test
    void passwordEncoderSuccessful(){
        assertAll(() -> applicationConfig.passwordEncoder());
    }
}
