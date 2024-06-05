package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Design pattern builder. Classe Builder dove vengono riportati esattamente tutti
 * gli attributi del model User e un rispettivo metodo (setter) per ognuno di loro.
 */
@Getter
@NoArgsConstructor
public class UserBuilder {
    // Attributi del model User
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

    /**
     * Chiamato prima del metodo build(), per settare l'attributo userId.
     * @param userId Valore dell'id univoco dell'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder userId(long userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo nome.
     * @param nome Nome dell'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo cognome.
     * @param cognome Cognome dell'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo email.
     * @param email Email dell'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo password.
     * @param password Password dell'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo username.
     * @param username Username dell'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo ruolo.
     * @param ruolo Ruolo dell'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder ruolo(Ruolo ruolo) {
        this.ruolo = ruolo;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo dataNascita.
     * @param dataNascita Data di nascita dell'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder dataNascita(LocalDateTime dataNascita) {
        this.dataNascita = dataNascita;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo dataRegistrazione.
     * @param dataRegistrazione Data di registrazione dell'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder dataRegistrazione(LocalDateTime dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo saldo.
     * @param saldo Saldo dell'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder saldo(Double saldo) {
        this.saldo = saldo;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo tavoli.
     * @param tavoli Lista dei tavoli in cui ha giocato il player.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder tavoli(List<Tavolo> tavoli) {
        this.tavoli = tavoli;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo messaggi.
     * @param messaggi Lista dei messaggi inviati dall'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder messaggi(List<Messaggio> messaggi) {
        this.messaggi = messaggi;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo ricariche.
     * @param ricariche Lista delle ricariche effettuate dall'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder ricariche(List<Ricarica> ricariche) {
        this.ricariche = ricariche;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo tabacchi.
     * @param tabacchi Lista dei tabacchi gestiti dall'economo.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder tabacchi(List<Tabacchi> tabacchi) {
        this.tabacchi = tabacchi;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo notifiche.
     * @param notifiche Lista delle notifiche ricevute dall'utente.
     * @return Un'istanza della classe stessa.
     */
    public UserBuilder notifiche(List<Notifica> notifiche) {
        this.notifiche = notifiche;
        return this;
    }

    /**
     * Metodo che costruisce l'oggetto {@link User} con tutti i dati settati in precedenza.
     * @return Un'istanza di User a cui viene passato, tramite il costruttore, l'istanza di questa classe.
     */
    public User build(){
        return new User(this);
    }
}
