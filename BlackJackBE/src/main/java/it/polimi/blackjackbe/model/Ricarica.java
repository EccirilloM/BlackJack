package it.polimi.blackjackbe.model;

import it.polimi.blackjackbe.builder.RicaricaBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ricarica")
@Entity(name = "Ricarica")
public class Ricarica {
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

    @Column(updatable = false)
    private Double importo;

    @Column(updatable = false)
    private LocalDateTime dataRicarica;

    @Column(updatable = false)
    private LocalDateTime dataRichiesta;

    @Column(nullable = false)
    private boolean richiesta;

    @Column
    private boolean accettata;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User player;

    @ManyToOne
    @JoinColumn(name = "tabacchi_id")
    private Tabacchi tabacchi;

    public Ricarica(RicaricaBuilder ricaricaBuilder) {
        this.ricaricaId = ricaricaBuilder.getRicaricaId();
        this.importo = ricaricaBuilder.getImporto();
        this.dataRicarica = ricaricaBuilder.getDataRicarica();
        this.dataRichiesta = ricaricaBuilder.getDataRichiesta();
        this.richiesta = ricaricaBuilder.isRichiesta();
        this.accettata = ricaricaBuilder.isAccettata();
        this.player = ricaricaBuilder.getPlayer();
        this.tabacchi = ricaricaBuilder.getTabacchi();
    }

}
