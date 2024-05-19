package it.polimi.blackjackbe.unit.service.implementation;

import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.security.utils.JwtUtils;
import it.polimi.blackjackbe.service.implementation.AuthServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplementationTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    AuthServiceImplementation authServiceImplementation;



}
