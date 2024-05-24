package it.polimi.blackjackbe.model;

import it.polimi.blackjackbe.builder.ManoBuilder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "Mano")
@Entity(name = "Mano")
public class Mano {
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

    @ManyToOne
    @JoinColumn(name = "tavolo_id")
    private Tavolo tavolo;
    //Da qui posso ripescarmi sia l'utente e il tipotavolo

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataMano;

    @Column(nullable = false, updatable = false)
    private Double importo; //Importo vinto o perso  dal casino

    public Mano(ManoBuilder builder) {
        this.manoId = builder.getManoId();
        this.tavolo = builder.getTavolo();
        this.dataMano = builder.getDataMano();
        this.importo = builder.getImporto();
    }
}