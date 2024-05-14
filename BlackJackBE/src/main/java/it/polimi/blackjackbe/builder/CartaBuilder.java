package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Carta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartaBuilder {
    private String seme;
    private String valore;
    private int punteggio;

    public CartaBuilder seme(String seme) {
        this.seme = seme;
        return this;
    }

    public CartaBuilder valore(String valore) {
        this.valore = valore;
        return this;
    }

    public CartaBuilder punteggio(int punteggio) {
        this.punteggio = punteggio;
        return this;
    }

    public Carta build() {
        return new Carta(this);
    }

}
