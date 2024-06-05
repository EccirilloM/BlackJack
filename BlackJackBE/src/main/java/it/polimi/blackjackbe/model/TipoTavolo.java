package it.polimi.blackjackbe.model;

import lombok.Data;
import lombok.Getter;

/**
 * Enum che rappresenta i diversi tipi di tavoli nel gioco del Blackjack.
 * Ogni tipo di tavolo ha un numero di mazzi e una puntata minima associati.
 */
@Getter
public enum TipoTavolo {
    /**
     * Tavolo base con 6 mazzi e puntata minima di 1.
     */
    BASE(6, 1),

    /**
     * Tavolo premium con 4 mazzi e puntata minima di 5.
     */
    PREMIUM(4, 5),

    /**
     * Tavolo VIP con 3 mazzi e puntata minima di 10.
     */
    VIP(3, 10),

    /**
     * Tavolo esclusivo con 2 mazzi e puntata minima di 20.
     */
    EXCLUSIVE(2, 20);

    private final int numeroDiMazzi;
    private final int puntataMinima;

    /**
     * Costruttore per l'enumerazione TipoTavolo.
     * @param numeroDiMazzi Il numero di mazzi associati al tipo di tavolo.
     * @param puntataMinima La puntata minima associata al tipo di tavolo.
     */
    private TipoTavolo(int numeroDiMazzi, int puntataMinima) {
        this.numeroDiMazzi = numeroDiMazzi;
        this.puntataMinima = puntataMinima;
    }
}
