package it.polimi.blackjackbe.model;

import it.polimi.blackjackbe.builder.ManoBuilder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Model che rappresenta una mano nel gioco del Blackjack.
 * Questa classe utilizza il pattern Builder per creare oggetti di tipo Mano.
 */
@Data
@NoArgsConstructor
@Table(name = "Mano")
@Entity(name = "Mano")
public class Mano {

    /**
     * Identificatore univoco per ogni mano.
     * Configurato con autoincremento utilizzando una sequenza.
     */
    @Id
    @SequenceGenerator(
            name = "mano_sequence",
            sequenceName = "mano_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mano_sequence"
    )
    @Column(updatable = false, nullable = false, name = "mano_id")
    private Long manoId;

    /**
     * Tavolo associato a questa mano.
     * Da qui è possibile risalire sia all'utente che al tipo di tavolo.
     */
    @ManyToOne
    @JoinColumn(name = "tavolo_id")
    private Tavolo tavolo;

    /**
     * Data in cui è stata giocata la mano.
     * Deve essere valorizzata e non può essere modificata una volta impostata.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataMano;

    /**
     * Importo vinto o perso dal casinò in questa mano.
     * Deve essere valorizzato e non può essere modificato una volta impostato.
     */
    @Column(nullable = false, updatable = false)
    private Double importo;

    /**
     * Costruttore che utilizza il builder per assegnare i valori agli attributi della mano.
     * @param builder Dati appena settati tramite il pattern builder {@link ManoBuilder}.
     */
    public Mano(ManoBuilder builder) {
        // Assegna l'identificatore univoco della mano dalla classe builder
        this.manoId = builder.getManoId();
        // Assegna il tavolo associato alla mano dalla classe builder
        this.tavolo = builder.getTavolo();
        // Assegna la data della mano dalla classe builder
        this.dataMano = builder.getDataMano();
        // Assegna l'importo della mano dalla classe builder
        this.importo = builder.getImporto();
    }
}
