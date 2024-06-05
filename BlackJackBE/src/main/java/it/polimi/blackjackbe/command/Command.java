package it.polimi.blackjackbe.command;

import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import java.util.Map;

/**
 * Classe astratta che rappresenta un comando nel gioco del Blackjack.
 * Le classi concrete che estendono questa classe devono implementare il metodo execute.
 */
public abstract class Command {
    /**
     * Esegue il comando specifico.
     * @param userId ID dell'utente che richiede il comando.
     * @param data Dati aggiuntivi richiesti per l'esecuzione del comando.
     * @return La risposta con lo stato del tavolo dopo l'esecuzione del comando.
     */
    public abstract TavoloStatusResponse execute(Long userId, Map<String, Object> data);
}
