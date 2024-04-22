package it.polimi.blackjackbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messaggio")
@Entity(name = "Messaggio")
public class Messaggio {
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
    @Column(updatable = false, nullable = false)
    private Long messaggioId;

    @Column(nullable = false, updatable = false)
    private String testo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataInvio;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private TipoTavolo tipoTavolo;
}
