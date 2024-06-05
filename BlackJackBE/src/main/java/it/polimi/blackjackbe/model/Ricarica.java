package it.polimi.blackjackbe.model;

import it.polimi.blackjackbe.builder.RicaricaBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Model che rappresenta una ricarica nel sistema.
 * Questa classe utilizza il pattern Builder per creare oggetti di tipo Ricarica.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ricarica")
@Entity(name = "Ricarica")
public class Ricarica {

    /**
     * Identificatore univoco per ogni ricarica.
     * Configurato con autoincremento utilizzando una sequenza.
     */
    @Id
    @SequenceGenerator(
            name = "ricarica_sequence",
            sequenceName = "ricarica_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ricarica_sequence"
    )
    @Column(updatable = false, nullable = false, name = "ricarica_id")
    private Long ricaricaId;

    /**
     * Importo della ricarica.
     * Non può essere modificato una volta impostato.
     */
    @Column(updatable = false)
    private Double importo;

    /**
     * Data in cui è stata effettuata la ricarica.
     * Non può essere modificata una volta impostata.
     */
    @Column(updatable = false)
    private LocalDateTime dataRicarica;

    /**
     * Data in cui è stata richiesta la ricarica.
     * Non può essere modificata una volta impostata.
     */
    @Column(updatable = false)
    private LocalDateTime dataRichiesta;

    /**
     * Indica se la ricarica è stata richiesta.
     * Deve essere valorizzato.
     */
    @Column(nullable = false)
    private boolean richiesta;

    /**
     * Indica se la ricarica è stata accettata.
     */
    @Column
    private boolean accettata;

    /**
     * Utente che ha richiesto la ricarica.
     * Associa la ricarica a un utente tramite una relazione Many-to-One.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User player;

    /**
     * Tabacchi associato alla ricarica.
     * Associa la ricarica a un tabacchi tramite una relazione Many-to-One.
     */
    @ManyToOne
    @JoinColumn(name = "tabacchi_id")
    private Tabacchi tabacchi;

    /**
     * Costruttore che utilizza il builder per assegnare i valori agli attributi della ricarica.
     * @param ricaricaBuilder Dati appena settati tramite il pattern builder {@link RicaricaBuilder}.
     */
    public Ricarica(RicaricaBuilder ricaricaBuilder) {
        // Assegna l'identificatore univoco della ricarica dalla classe builder
        this.ricaricaId = ricaricaBuilder.getRicaricaId();
        // Assegna l'importo della ricarica dalla classe builder
        this.importo = ricaricaBuilder.getImporto();
        // Assegna la data della ricarica dalla classe builder
        this.dataRicarica = ricaricaBuilder.getDataRicarica();
        // Assegna la data della richiesta dalla classe builder
        this.dataRichiesta = ricaricaBuilder.getDataRichiesta();
        // Assegna lo stato di richiesta dalla classe builder
        this.richiesta = ricaricaBuilder.isRichiesta();
        // Assegna lo stato di accettazione dalla classe builder
        this.accettata = ricaricaBuilder.isAccettata();
        // Assegna l'utente che ha richiesto la ricarica dalla classe builder
        this.player = ricaricaBuilder.getPlayer();
        // Assegna il tabacchi associato alla ricarica dalla classe builder
        this.tabacchi = ricaricaBuilder.getTabacchi();
    }
}
