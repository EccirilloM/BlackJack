package it.polimi.blackjackbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tavolo")
@Entity(name = "Tavolo")
public class Tavolo {
    @Id
    @SequenceGenerator(
            name = "tavolo_sequence",
            sequenceName = "tavolo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tavolo_sequence"
    )
    @Column(updatable = false, nullable = false)
    private Long tavoloId;


    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private TipoTavolo tipoTavolo;

    @Column(nullable = false, updatable = false)
    private String descrizione;
}
