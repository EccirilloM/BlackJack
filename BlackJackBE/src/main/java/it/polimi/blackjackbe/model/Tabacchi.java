package it.polimi.blackjackbe.model;

import it.polimi.blackjackbe.builder.TabacchiBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Model che rappresenta un tabacchi nel sistema.
 * Questa classe utilizza il pattern Builder per creare oggetti di tipo Tabacchi.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tabacchi")
@Entity(name = "Tabacchi")
public class Tabacchi {

    /**
     * Identificatore univoco per ogni tabacchi.
     * Configurato con autoincremento utilizzando una sequenza.
     */
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

    /**
     * Nome del tabacchi.
     * Non può essere modificato una volta impostato.
     */
    @Column(nullable = false, updatable = false)
    private String nome;

    /**
     * Latitudine della posizione del tabacchi.
     * Non può essere modificata una volta impostata.
     */
    @Column(nullable = false, updatable = false)
    private Double lat;

    /**
     * Longitudine della posizione del tabacchi.
     * Non può essere modificata una volta impostata.
     */
    @Column(nullable = false, updatable = false)
    private Double lng;

    /**
     * Ricariche effettuate nel singolo tabacchi.
     * Relazione One-to-Many con la classe {@link Ricarica}.
     */
    @OneToMany(mappedBy = "tabacchi", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ricarica> ricariche;

    /**
     * Economo che gestisce il tabacchi.
     * Relazione Many-to-One con la classe {@link User}.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User economo;

    /**
     * Costruttore che utilizza il builder per assegnare i valori agli attributi del tabacchi.
     * @param tabacchiBuilder Dati appena settati tramite il pattern builder {@link TabacchiBuilder}.
     */
    public Tabacchi(TabacchiBuilder tabacchiBuilder) {
        // Assegna l'identificatore univoco del tabacchi dalla classe builder
        this.tabacchiId = tabacchiBuilder.getTabacchiId();
        // Assegna il nome del tabacchi dalla classe builder
        this.nome = tabacchiBuilder.getNome();
        // Assegna la latitudine della posizione del tabacchi dalla classe builder
        this.lat = tabacchiBuilder.getLat();
        // Assegna la longitudine della posizione del tabacchi dalla classe builder
        this.lng = tabacchiBuilder.getLng();
        // Assegna le ricariche effettuate nel singolo tabacchi dalla classe builder
        this.ricariche = tabacchiBuilder.getRicariche();
        // Assegna l'economo che gestisce il tabacchi dalla classe builder
        this.economo = tabacchiBuilder.getEconomo();
    }
}
