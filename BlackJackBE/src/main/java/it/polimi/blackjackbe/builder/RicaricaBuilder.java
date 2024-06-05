package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Ricarica;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Design pattern builder. Classe Builder dove vengono riportati esattamente tutti
 * gli attributi del model Ricarica e un rispettivo metodo (setter) per ognuno di loro.
 */
@Getter
@NoArgsConstructor
public class RicaricaBuilder {
    // Attributi del model Ricarica
    private Long ricaricaId;
    private Double importo;
    private LocalDateTime dataRicarica;
    private LocalDateTime dataRichiesta;
    private boolean richiesta;
    private boolean accettata;
    private User player;
    private Tabacchi tabacchi;

    /**
     * Chiamato prima del metodo build(), per settare l'attributo ricaricaId.
     * @param ricaricaId Valore dell'id univoco della ricarica.
     * @return Un'istanza della classe stessa.
     */
    public RicaricaBuilder ricaricaId(Long ricaricaId) {
        this.ricaricaId = ricaricaId;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo importo.
     * @param importo Importo della ricarica.
     * @return Un'istanza della classe stessa.
     */
    public RicaricaBuilder importo(Double importo) {
        this.importo = importo;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo dataRicarica.
     * @param dataRicarica Data e ora in cui è stata effettuata la ricarica.
     * @return Un'istanza della classe stessa.
     */
    public RicaricaBuilder dataRicarica(LocalDateTime dataRicarica) {
        this.dataRicarica = dataRicarica;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo dataRichiesta.
     * @param dataRichiesta Data e ora in cui è stata richiesta la ricarica.
     * @return Un'istanza della classe stessa.
     */
    public RicaricaBuilder dataRichiesta(LocalDateTime dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo richiesta.
     * @param richiesta Booleano che indica se la ricarica è stata richiesta.
     * @return Un'istanza della classe stessa.
     */
    public RicaricaBuilder richiesta(boolean richiesta) {
        this.richiesta = richiesta;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo accettata.
     * @param accettata Booleano che indica se la ricarica è stata accettata.
     * @return Un'istanza della classe stessa.
     */
    public RicaricaBuilder accettata(boolean accettata) {
        this.accettata = accettata;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo player.
     * @param player Utente associato alla ricarica.
     * @return Un'istanza della classe stessa.
     */
    public RicaricaBuilder player(User player) {
        this.player = player;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo tabacchi.
     * @param tabacchi Tabacchi associato alla ricarica.
     * @return Un'istanza della classe stessa.
     */
    public RicaricaBuilder tabacchi(Tabacchi tabacchi) {
        this.tabacchi = tabacchi;
        return this;
    }

    /**
     * Metodo che costruisce l'oggetto {@link Ricarica} con tutti i dati settati in precedenza.
     * @return Un'istanza di Ricarica a cui viene passato, tramite il costruttore, l'istanza di questa classe.
     */
    public Ricarica build() {
        return new Ricarica(this);
    }
}
