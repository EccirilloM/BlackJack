package it.polimi.blackjackbe.model;

import it.polimi.blackjackbe.builder.UserBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Table(name = "_user",
    uniqueConstraints = {
        @UniqueConstraint(name = "utente_username_unique", columnNames = "username")
    }
)


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(updatable = false, nullable = false, name = "user_id")
    private long userId;

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

    @Column(nullable = false)
    private Double saldo;

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    private List<Tavolo> tavoli; //Tavoli in cui ha giocato il player

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Messaggio> messaggi; //Messaggi inviati dall'utente

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    private List<Ricarica> ricariche; //Ricariche effettuate dall'utente

    @OneToMany(mappedBy = "economo", fetch = FetchType.LAZY)
    private List<Tabacchi> tabacchi; //Tabacchi gestiti dall'economo

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    private List<Notifica> notifiche; //Notifiche ricevute dal player

    public User (UserBuilder userBuilder) {
        this.userId = userBuilder.getUserId();
        this.nome = userBuilder.getNome();
        this.cognome = userBuilder.getCognome();
        this.email = userBuilder.getEmail();
        this.password = userBuilder.getPassword();
        this.username = userBuilder.getUsername();
        this.ruolo = userBuilder.getRuolo();
        this.dataNascita = userBuilder.getDataNascita();
        this.dataRegistrazione = userBuilder.getDataRegistrazione();
        this.saldo = userBuilder.getSaldo();
        this.tavoli = userBuilder.getTavoli();
        this.messaggi = userBuilder.getMessaggi();
        this.ricariche = userBuilder.getRicariche();
        this.tabacchi = userBuilder.getTabacchi();
        this.notifiche = userBuilder.getNotifiche();
    }

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