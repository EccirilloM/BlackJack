package it.polimi.blackjackbe.model;

import it.polimi.blackjackbe.builder.CartaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model che rappresenta una carta nel gioco del Blackjack.
 * Questa classe utilizza il pattern Builder per creare oggetti di tipo Carta.
 */
@Data
@AllArgsConstructor
public class Carta {
    private String seme;
    private String valore;
    private int punteggio;
    private int order;

    /**
     * Costruttore che utilizza il builder per assegnare i valori agli attributi della carta.
     * @param builder Dati appena settati tramite il pattern builder {@link CartaBuilder}.
     */
    public Carta(CartaBuilder builder) {
        // Assegna il valore del seme dalla classe builder
        this.seme = builder.getSeme();
        // Assegna il valore della carta dalla classe builder
        this.valore = builder.getValore();
        // Assegna il punteggio della carta dalla classe builder
        this.punteggio = builder.getPunteggio();
        // Assegna l'ordine della carta dalla classe builder
        this.order = builder.getOrder();
    }
}
