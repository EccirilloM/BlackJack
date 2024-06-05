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

/**
 * Model che rappresenta un utente nel sistema.
 * Questa classe implementa {@link UserDetails} per integrarsi con Spring Security.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * Identificatore univoco per ogni utente.
     * Configurato con autoincremento utilizzando una sequenza.
     */
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

    /**
     * Nome dell'utente.
     * Non deve essere vuoto.
     */
    @Column(nullable = false)
    private String nome;

    /**
     * Cognome dell'utente.
     * Non deve essere vuoto.
     */
    @Column(nullable = false)
    private String cognome;

    /**
     * Email dell'utente.
     * Non deve essere vuota.
     */
    @Column(nullable = false)
    private String email;

    /**
     * Password dell'utente.
     * Non deve essere vuota.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Username dell'utente.
     * Non deve essere vuoto.
     */
    @Column(nullable = false)
    private String username;

    /**
     * Ruolo dell'utente.
     * Utilizza un enumerato per limitare i valori possibili.
     */
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    /**
     * Data di nascita dell'utente.
     * Non deve essere vuota.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataNascita;

    /**
     * Data di registrazione dell'utente.
     * Non deve essere vuota.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataRegistrazione;

    /**
     * Saldo dell'utente.
     * Non deve essere vuoto.
     */
    @Column(nullable = false)
    private Double saldo;

    /**
     * Tavoli in cui ha giocato il giocatore.
     * Relazione One-to-Many con la classe {@link Tavolo}.
     */
    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    private List<Tavolo> tavoli;

    /**
     * Messaggi inviati dall'utente.
     * Relazione One-to-Many con la classe {@link Messaggio}.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Messaggio> messaggi;

    /**
     * Ricariche effettuate dall'utente.
     * Relazione One-to-Many con la classe {@link Ricarica}.
     */
    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ricarica> ricariche;

    /**
     * Tabacchi gestiti dall'economo.
     * Relazione One-to-Many con la classe {@link Tabacchi}.
     */
    @OneToMany(mappedBy = "economo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tabacchi> tabacchi;

    /**
     * Notifiche ricevute dal giocatore.
     * Relazione One-to-Many con la classe {@link Notifica}.
     */
    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notifica> notifiche;

    /**
     * Costruttore che utilizza il builder per assegnare i valori agli attributi dell'utente.
     * @param userBuilder Dati appena settati tramite il pattern builder {@link UserBuilder}.
     */
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