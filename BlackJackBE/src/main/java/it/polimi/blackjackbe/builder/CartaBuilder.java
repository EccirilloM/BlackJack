package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Carta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Design pattern builder. Classe Builder dove vengono riportati esattamente tutti
 * gli attributi del model Carta e un rispettivo metodo (setter) per ognuno di loro.
 */
@Getter
@NoArgsConstructor
public class CartaBuilder {
    // Attributi del model Carta
    private String seme;
    private String valore;
    private int punteggio;
    private int order;

    /**
     * Chiamato prima del metodo build(), per settare l'attributo seme.
     * @param seme Valore del seme della carta.
     * @return Un'istanza della classe stessa.
     */
    public CartaBuilder seme(String seme) {
        this.seme = seme;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo valore.
     * @param valore Valore della carta.
     * @return Un'istanza della classe stessa.
     */
    public CartaBuilder valore(String valore) {
        this.valore = valore;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo punteggio.
     * @param punteggio Punteggio della carta.
     * @return Un'istanza della classe stessa.
     */
    public CartaBuilder punteggio(int punteggio) {
        this.punteggio = punteggio;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo order.
     * @param order Ordine della carta nel mazzo.
     * @return Un'istanza della classe stessa.
     */
    public CartaBuilder order(int order) {
        this.order = order;
        return this;
    }

    /**
     * Metodo che costruisce l'oggetto {@link Carta} con tutti i dati settati in precedenza.
     * @return Un'istanza di Carta a cui viene passato, tramite il costruttore, l'istanza di questa classe.
     */
    public Carta build() {
        return new Carta(this);
    }

}
