package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Notifica;
import it.polimi.blackjackbe.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Design pattern builder. Classe Builder dove vengono riportati esattamente tutti
 * gli attributi del model Notifica e un rispettivo metodo (setter) per ognuno di loro.
 */
@Getter
@NoArgsConstructor
public class NotificaBuilder {
    // Attributi del model Notifica
    private Long notificaId;
    private LocalDateTime data;
    private String testo;
    private User player;

    /**
     * Chiamato prima del metodo build(), per settare l'attributo notificaId.
     * @param notificaId Valore dell'id univoco della notifica.
     * @return Un'istanza della classe stessa.
     */
    public NotificaBuilder notificaId(Long notificaId) {
        this.notificaId = notificaId;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo data.
     * @param data Data e ora in cui la notifica è stata creata.
     * @return Un'istanza della classe stessa.
     */
    public NotificaBuilder data(LocalDateTime data) {
        this.data = data;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo testo.
     * @param testo Testo della notifica.
     * @return Un'istanza della classe stessa.
     */
    public NotificaBuilder testo(String testo) {
        this.testo = testo;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo player.
     * @param player Utente associato alla notifica.
     * @return Un'istanza della classe stessa.
     */
    public NotificaBuilder player(User player) {
        this.player = player;
        return this;
    }

    /**
     * Metodo che costruisce l'oggetto {@link Notifica} con tutti i dati settati in precedenza.
     * @return Un'istanza di Notifica a cui viene passato, tramite il costruttore, l'istanza di questa classe.
     */
    public Notifica build() {
        return new Notifica(this);
    }
}
