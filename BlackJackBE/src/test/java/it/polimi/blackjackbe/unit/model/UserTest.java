package it.polimi.blackjackbe.unit.model;

import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Test
    void testUser() {
        User user = new User(1L, "Mario", "Rossi", "email", "password", "username", Ruolo.ADMIN, LocalDateTime.now(), LocalDateTime.now(), 0.0, null, null, List.of(), null, null);
        String password = user.getPassword();
        String username = user.getUsername();
        boolean isAccountNonExpired = user.isAccountNonExpired();
        boolean isAccountNonLocked = user.isAccountNonLocked();
        boolean isCredentialsNonExpired = user.isCredentialsNonExpired();
        boolean isEnabled = user.isEnabled();
        Collection<? extends GrantedAuthority> a = user.getAuthorities();
    }
}
