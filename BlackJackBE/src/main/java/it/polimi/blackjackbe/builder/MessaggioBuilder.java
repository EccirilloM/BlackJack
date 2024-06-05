package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Messaggio;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Design pattern builder. Classe Builder dove vengono riportati esattamente tutti
 * gli attributi del model Messaggio e un rispettivo metodo (setter) per ognuno di loro.
 */
@Getter
@NoArgsConstructor
public class MessaggioBuilder {
    // Attributi del model Messaggio
    private Long messaggioId;
    private String testo;
    private LocalDateTime createdAt;
    private TipoTavolo tipoTavolo;
    private User user; // user che ha inviato il messaggio

    /**
     * Chiamato prima del metodo build(), per settare l'attributo messaggioId.
     * @param messaggioId Valore dell'id univoco del messaggio.
     * @return Un'istanza della classe stessa.
     */
    public MessaggioBuilder messaggioId(Long messaggioId) {
        this.messaggioId = messaggioId;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo testo.
     * @param testo Testo del messaggio.
     * @return Un'istanza della classe stessa.
     */
    public MessaggioBuilder testo(String testo) {
        this.testo = testo;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo createdAt.
     * @param createdAt Data e ora in cui il messaggio è stato creato.
     * @return Un'istanza della classe stessa.
     */
    public MessaggioBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo tipoTavolo.
     * @param tipoTavolo Tipo di tavolo associato al messaggio.
     * @return Un'istanza della classe stessa.
     */
    public MessaggioBuilder tipoTavolo(TipoTavolo tipoTavolo) {
        this.tipoTavolo = tipoTavolo;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo user.
     * @param user Utente che ha inviato il messaggio.
     * @return Un'istanza della classe stessa.
     */
    public MessaggioBuilder user(User user) {
        this.user = user;
        return this;
    }

    /**
     * Metodo che costruisce l'oggetto {@link Messaggio} con tutti i dati settati in precedenza.
     * @return Un'istanza di Messaggio a cui viene passato, tramite il costruttore, l'istanza di questa classe.
     */
    public Messaggio build() {
        return new Messaggio(this);
    }
}
