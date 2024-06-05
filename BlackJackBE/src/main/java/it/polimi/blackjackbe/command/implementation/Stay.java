package it.polimi.blackjackbe.command.implementation;

import it.polimi.blackjackbe.command.Command;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Classe che implementa il comando di "Stay" per il gioco del Blackjack.
 * Questa classe esegue l'azione quando l'utente decide di rimanere con le carte attuali e lascia che il dealer giochi.
 */
@AllArgsConstructor
@Component
@Transactional
public class Stay extends Command {
    private final TavoloServiceImplementation tavoloServiceImplementation;

    /**
     * Esegue il comando di Stay.
     * @param userId ID dell'utente che richiede il comando.
     * @param data Dati aggiuntivi richiesti per l'esecuzione del comando.
     * @return La risposta con lo stato del tavolo dopo l'esecuzione del comando.
     */
    @Override
    public TavoloStatusResponse execute(Long userId, Map<String, Object> data) {
        // Ottiene il tavolo associato all'utente usando il servizio tavolo
        Tavolo tavolo = tavoloServiceImplementation.getTavolo(userId);

        // Ottiene il punteggio attuale dell'utente
        var punteggioUtente = tavolo.punteggioUtente();

        // Il dealer continua a pescare carte finché il punteggio è inferiore a 17
        while (tavolo.punteggioDealer() < 17) {
            tavolo.pescaDealer();
        }

        // Ottiene il punteggio attuale del dealer dopo aver pescato le carte
        var punteggioDealer = tavolo.punteggioDealer();
        TavoloStatus tavoloStatus;

        // Se il punteggio del dealer supera 21, l'utente vince
        if (punteggioDealer > 21) {
            tavoloStatus = TavoloStatus.PLAYER_WIN;
            // Aggiorna lo stato del tavolo e processa la vittoria dell'utente
            tavoloServiceImplementation.processWin(tavolo, tavoloStatus);
        } else {
            // Confronta la differenza tra i punteggi di utente e dealer rispetto a 21
            if (Math.abs(punteggioUtente - 21) < Math.abs(punteggioDealer - 21)) {
                // Se l'utente è più vicino a 21 rispetto al dealer, l'utente vince
                tavoloStatus = TavoloStatus.PLAYER_WIN;
                tavoloServiceImplementation.processWin(tavolo, tavoloStatus);
            } else if (Math.abs(punteggioUtente - 21) == Math.abs(punteggioDealer - 21)) {
                // Se l'utente e il dealer hanno lo stesso punteggio, è un pareggio
                tavoloStatus = TavoloStatus.DRAW;
                tavoloServiceImplementation.processWin(tavolo, tavoloStatus);
            } else {
                // Se il dealer è più vicino a 21 rispetto all'utente, l'utente perde
                tavoloStatus = TavoloStatus.PLAYER_LOSE;
                tavoloServiceImplementation.processWin(tavolo, tavoloStatus);
            }
        }

        // Ottiene la risposta sullo stato del tavolo dopo aver determinato il risultato
        TavoloStatusResponse tavoloStatusResponse = tavoloServiceImplementation.getTavoloStatusResponse(tavolo, tavoloStatus);

        // Pulisce il tavolo per prepararlo a una nuova partita
        tavolo.clear();

        // Ritorna la risposta con lo stato del tavolo
        return tavoloStatusResponse;
    }
}
