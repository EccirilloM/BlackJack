package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Mano;
import it.polimi.blackjackbe.model.Tavolo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Design pattern builder. Classe Builder dove vengono riportati esattamente tutti
 * gli attributi del model Mano e un rispettivo metodo (setter) per ognuno di loro.
 */
@Getter
@NoArgsConstructor
public class ManoBuilder {
    // Attributi del model Mano
    private Long manoId;
    private Tavolo tavolo;
    private LocalDateTime dataMano;
    private Double importo;

    /**
     * Chiamato prima del metodo build(), per settare l'attributo manoId.
     * @param manoId Valore dell'id univoco della mano.
     * @return Un'istanza della classe stessa.
     */
    public ManoBuilder manoId(Long manoId) {
        this.manoId = manoId;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo tavolo.
     * @param tavolo Istanza del tavolo associato alla mano.
     * @return Un'istanza della classe stessa.
     */
    public ManoBuilder tavolo(Tavolo tavolo) {
        this.tavolo = tavolo;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo dataMano.
     * @param dataMano Data e ora in cui è stata giocata la mano.
     * @return Un'istanza della classe stessa.
     */
    public ManoBuilder dataMano(LocalDateTime dataMano) {
        this.dataMano = dataMano;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo importo.
     * @param importo Importo della mano.
     * @return Un'istanza della classe stessa.
     */
    public ManoBuilder importo(Double importo) {
        this.importo = importo;
        return this;
    }

    /**
     * Metodo che costruisce l'oggetto {@link Mano} con tutti i dati settati in precedenza.
     * @return Un'istanza di Mano a cui viene passato, tramite il costruttore, l'istanza di questa classe.
     */
    public Mano build() {
        return new Mano(this);
    }
}
