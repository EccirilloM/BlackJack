package it.polimi.blackjackbe.model;

import lombok.Data;
import lombok.Getter;

@Getter
public enum TipoTavolo {
    BASE(6, 1),
    PREMIUM(4, 5),
    VIP(3, 10),
    EXCLUSIVE(2, 20);

    private int numeroDiMazzi;
    private int puntataMinima;

    private TipoTavolo(int numeroDiMazzi, int puntataMinima){
        this.numeroDiMazzi=numeroDiMazzi;
        this.puntataMinima=puntataMinima;
    }
}
