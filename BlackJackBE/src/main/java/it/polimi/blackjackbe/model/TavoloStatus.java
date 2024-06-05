package it.polimi.blackjackbe.model;

/**
 * Enum che rappresenta lo stato di un tavolo nel gioco del Blackjack.
 */
public enum TavoloStatus {
    /**
     * Il gioco continua.
     */
    CONTINUE,

    /**
     * Il giocatore ha vinto.
     */
    PLAYER_WIN,

    /**
     * Il giocatore ha perso.
     */
    PLAYER_LOSE,

    /**
     * La partita è terminata con un pareggio.
     */
    DRAW
}
