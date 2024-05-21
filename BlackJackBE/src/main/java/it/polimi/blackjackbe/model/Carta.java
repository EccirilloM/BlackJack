package it.polimi.blackjackbe.model;

import it.polimi.blackjackbe.builder.CartaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Carta {
    private String seme;
    private String valore;
    private int punteggio;
    private int order;

    public Carta(String seme, String valore, int punteggio) {
        this.seme = seme;
        this.valore = valore;
        this.punteggio = punteggio;
    }

    public Carta(CartaBuilder builder) {
        this.seme = builder.getSeme();
        this.valore = builder.getValore();
        this.punteggio = builder.getPunteggio();
    }
}
