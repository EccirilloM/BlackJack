package it.polimi.blackjackbe.model;

import it.polimi.blackjackbe.builder.NotificaBuilder;
import it.polimi.blackjackbe.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Model che rappresenta una notifica nel sistema.
 * Questa classe utilizza il pattern Builder per creare oggetti di tipo Notifica.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Notifica")
@Table(name = "notifica")
public class Notifica {

    /**
     * Identificatore univoco per ogni notifica.
     * Configurato con autoincremento utilizzando una sequenza.
     */
    @Id
    @SequenceGenerator(
            name = "notifica_sequence",
            sequenceName = "notifica_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "notifica_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "notifica_id", nullable = false, updatable = false)
    private Long notificaId;

    /**
     * Data in cui è stata creata la notifica.
     * Non può essere modificata una volta impostata.
     */
    @Column(name = "data", updatable = false)
    private LocalDateTime data;

    /**
     * Testo della notifica.
     * Non può essere modificato una volta impostato.
     */
    @Column(name = "testo", updatable = false, columnDefinition = "VARCHAR(1000)")
    private String testo;

    /**
     * Utente a cui è destinata la notifica.
     * Associa la notifica a un utente tramite una relazione Many-to-One.
     */
    @ManyToOne
    private User player;

    /**
     * Costruttore che utilizza il builder per assegnare i valori agli attributi della notifica.
     * @param builder Dati appena settati tramite il pattern builder {@link NotificaBuilder}.
     */
    public Notifica(NotificaBuilder builder) {
        // Assegna l'identificatore univoco della notifica dalla classe builder
        this.notificaId = builder.getNotificaId();
        // Assegna la data della notifica dalla classe builder
        this.data = builder.getData();
        // Assegna il testo della notifica dalla classe builder
        this.testo = builder.getTesto();
        // Assegna l'utente destinatario della notifica dalla classe builder
        this.player = builder.getPlayer();
    }
}