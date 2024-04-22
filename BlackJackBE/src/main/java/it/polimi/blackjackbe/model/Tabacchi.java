package it.polimi.blackjackbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tabacchi")
@Entity(name = "Tabacchi")
public class Tabacchi {
    @Id
    @SequenceGenerator(
            name = "tabacchi_sequence",
            sequenceName = "tabacchi_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tabacchi_sequence"
    )
    @Column(updatable = false, nullable = false, name = "tabacchi_id")
    private Long tabacchiId;

    @Column(nullable = false, updatable = false)
    private Float lat;

    @Column(nullable = false, updatable = false)
    private Float lng;

    @OneToMany(mappedBy = "tabacchi", fetch = FetchType.LAZY)
    private List<Ricarica> ricariche; //Ricariche effettuate nel singolo tabacchi

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User economo; //Economo che gestisce il tabacchi
}
