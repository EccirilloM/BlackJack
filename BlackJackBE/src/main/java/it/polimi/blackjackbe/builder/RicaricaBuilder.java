package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Ricarica;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RicaricaBuilder {
    private Long ricaricaId;
    private Double importo;
    private LocalDateTime dataRicarica;
    private LocalDateTime dataRichiesta;
    private boolean richiesta;
    private boolean accettata;
    private User player;
    private Tabacchi tabacchi;

    public RicaricaBuilder ricaricaId(Long ricaricaId) {
        this.ricaricaId = ricaricaId;
        return this;
    }

    public RicaricaBuilder importo(Double importo) {
        this.importo = importo;
        return this;
    }

    public RicaricaBuilder dataRicarica(LocalDateTime dataRicarica) {
        this.dataRicarica = dataRicarica;
        return this;
    }

    public RicaricaBuilder dataRichiesta(LocalDateTime dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
        return this;
    }

    public RicaricaBuilder richiesta(boolean richiesta) {
        this.richiesta = richiesta;
        return this;
    }

    public RicaricaBuilder accettata(boolean accettata) {
        this.accettata = accettata;
        return this;
    }

    public RicaricaBuilder player(User player) {
        this.player = player;
        return this;
    }

    public RicaricaBuilder tabacchi(Tabacchi tabacchi) {
        this.tabacchi = tabacchi;
        return this;
    }

    public Ricarica build() {
        return new Ricarica(this);
    }
}
