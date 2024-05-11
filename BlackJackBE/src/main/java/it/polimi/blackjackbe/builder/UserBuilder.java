package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserBuilder {
    private long userId;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String username;
    private Ruolo ruolo;
    private LocalDateTime dataNascita;
    private LocalDateTime dataRegistrazione;
    private Double saldo;
    private List<Tavolo> tavoli; //Tavoli in cui ha giocato il player
    private List<Messaggio> messaggi; //Messaggi inviati dall'utente
    private List<Ricarica> ricariche; //Ricariche effettuate dall'utente
    private List<Tabacchi> tabacchi; //Tabacchi gestiti dall'economo
    private List<Notifica> notifiche; //Notifiche ricevute dal player

    public UserBuilder userId(long userId) {
        this.userId = userId;
        return this;
    }

    public UserBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public UserBuilder cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder ruolo(Ruolo ruolo) {
        this.ruolo = ruolo;
        return this;
    }

    public UserBuilder dataNascita(LocalDateTime dataNascita) {
        this.dataNascita = dataNascita;
        return this;
    }

    public UserBuilder dataRegistrazione(LocalDateTime dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
        return this;
    }

    public UserBuilder saldo(Double saldo) {
        this.saldo = saldo;
        return this;
    }

    public UserBuilder tavoli(List<Tavolo> tavoli) {
        this.tavoli = tavoli;
        return this;
    }

    public UserBuilder messaggi(List<Messaggio> messaggi) {
        this.messaggi = messaggi;
        return this;
    }

    public UserBuilder ricariche(List<Ricarica> ricariche) {
        this.ricariche = ricariche;
        return this;
    }

    public UserBuilder tabacchi(List<Tabacchi> tabacchi) {
        this.tabacchi = tabacchi;
        return this;
    }

    public UserBuilder notifiche(List<Notifica> notifiche) {
        this.notifiche = notifiche;
        return this;
    }

    public User build(){
        return new User(this);
    }
}
