package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Mano;
import it.polimi.blackjackbe.model.Tavolo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ManoBuilder {
    private Long manoId;
    private Tavolo tavolo;
    private LocalDateTime dataMano;
    private Double importo;

    public ManoBuilder manoId(Long manoId) {
        this.manoId = manoId;
        return this;
    }

    public ManoBuilder tavolo(Tavolo tavolo) {
        this.tavolo = tavolo;
        return this;
    }

    public ManoBuilder dataMano(LocalDateTime dataMano) {
        this.dataMano = dataMano;
        return this;
    }

    public ManoBuilder importo(Double importo) {
        this.importo = importo;
        return this;
    }

    public Mano build() {
        return new Mano(this);
    }
}
