package it.polimi.blackjackbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity(name = "Utente")
@Table(name = "utente",
    uniqueConstraints = {
        @UniqueConstraint(name = "utente_email_unique", columnNames = "email"),
        @UniqueConstraint(name = "utente_username_unique", columnNames = "username")
    }
)


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "utente_sequence",
            sequenceName = "utente_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "utente_sequence"
    )
    @Column(updatable = false, nullable = false, unique = true)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataNascita;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataRegistrazione;

    @Column()
    private Double saldo;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + ruolo.name().toUpperCase()));
    }
}