package it.polimi.blackjackbe.model;

import it.polimi.blackjackbe.builder.MessaggioBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Model che rappresenta un messaggio nel sistema.
 * Questa classe utilizza il pattern Builder per creare oggetti di tipo Messaggio.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messaggio")
@Entity(name = "Messaggio")
public class Messaggio {

    /**
     * Identificatore univoco per ogni messaggio.
     * Configurato con autoincremento utilizzando una sequenza.
     */
    @Id
    @SequenceGenerator(
            name = "messaggio_sequence",
            sequenceName = "messaggio_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "messaggio_sequence"
    )
    @Column(updatable = false, nullable = false, name = "messaggio_id")
    private Long messaggioId;

    /**
     * Testo del messaggio.
     * Non deve essere vuoto e non può essere modificato una volta impostato.
     */
    @Column(nullable = false, updatable = false)
    private String testo;

    /**
     * Data di creazione del messaggio.
     * Non deve essere vuota e non può essere modificata una volta impostata.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Tipo di tavolo associato al messaggio.
     * Utilizza un enumerato per limitare i valori possibili.
     */
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private TipoTavolo tipoTavolo;

    /**
     * Utente che ha inviato il messaggio.
     * Associa il messaggio a un utente tramite una relazione Many-to-One.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Costruttore che utilizza il builder per assegnare i valori agli attributi del messaggio.
     * @param messaggioBuilder Dati appena settati tramite il pattern builder {@link MessaggioBuilder}.
     */
    public Messaggio(MessaggioBuilder messaggioBuilder) {
        // Assegna l'identificatore univoco del messaggio dalla classe builder
        this.messaggioId = messaggioBuilder.getMessaggioId();
        // Assegna il testo del messaggio dalla classe builder
        this.testo = messaggioBuilder.getTesto();
        // Assegna la data di creazione del messaggio dalla classe builder
        this.createdAt = messaggioBuilder.getCreatedAt();
        // Assegna il tipo di tavolo associato al messaggio dalla classe builder
        this.tipoTavolo = messaggioBuilder.getTipoTavolo();
        // Assegna l'utente che ha inviato il messaggio dalla classe builder
        this.user = messaggioBuilder.getUser();
    }
}
