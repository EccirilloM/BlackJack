package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Carta;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Design pattern builder. Classe Builder dove vengono riportati esattamente tutti
 * gli attributi del model Tavolo e un rispettivo metodo (setter) per ognuno di loro.
 */
@Getter
@NoArgsConstructor
public class TavoloBuilder {
    // Attributi del model Tavolo
    private Long tavoloId;
    private TipoTavolo tipoTavolo;
    private User player;
    private List<Carta> carte;
    private List<Carta> carteSingolaMano;
    private List<Carta> cartaDealer;

    /**
     * Chiamato prima del metodo build(), per settare l'attributo tavoloId.
     * @param tavoloId Valore dell'id univoco del tavolo.
     * @return Un'istanza della classe stessa.
     */
    public TavoloBuilder tavoloId(Long tavoloId) {
        this.tavoloId = tavoloId;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo tipoTavolo.
     * @param tipoTavolo Tipo di tavolo.
     * @return Un'istanza della classe stessa.
     */
    public TavoloBuilder tipoTavolo(TipoTavolo tipoTavolo) {
        this.tipoTavolo = tipoTavolo;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo player.
     * @param player Utente associato al tavolo.
     * @return Un'istanza della classe stessa.
     */
    public TavoloBuilder player(User player) {
        this.player = player;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo carte.
     * @param carte Lista delle carte associate al tavolo.
     * @return Un'istanza della classe stessa.
     */
    public TavoloBuilder carte(List<Carta> carte) {
        this.carte = carte;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo carteSingolaMano.
     * @param carteSingolaMano Lista delle carte della singola mano.
     * @return Un'istanza della classe stessa.
     */
    public TavoloBuilder carteSingolaMano(List<Carta> carteSingolaMano) {
        this.carteSingolaMano = carteSingolaMano;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo cartaDealer.
     * @param cartaDealer Lista delle carte del dealer.
     * @return Un'istanza della classe stessa.
     */
    public TavoloBuilder cartaDealer(List<Carta> cartaDealer) {
        this.cartaDealer = cartaDealer;
        return this;
    }

    /**
     * Metodo che costruisce l'oggetto {@link Tavolo} con tutti i dati settati in precedenza.
     * @return Un'istanza di Tavolo a cui viene passato, tramite il costruttore, l'istanza di questa classe.
     */
    public Tavolo build() {
        return new Tavolo(this);
    }
}
