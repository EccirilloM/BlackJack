package it.polimi.blackjackbe.model;

import lombok.Data;
import lombok.Getter;

@Getter
public enum TipoTavolo {
    BASE(6),
    PREMIUM(4),
    VIP(3),
    EXCLUSIVE(2);

    private int numeroDiMazzi;

    private TipoTavolo(int numeroDiMazzi){
        this.numeroDiMazzi=numeroDiMazzi;
    }
}
