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
 * Classe che implementa il comando di "Hit" per il gioco del Blackjack.
 * Questa classe esegue l'azione di distribuire una carta aggiuntiva all'utente.
 */
@Component
@Transactional
@AllArgsConstructor
public class Hit extends Command {
    private final TavoloServiceImplementation tavoloServiceImplementation;

    /**
     * Esegue il comando di Hit.
     * @param userId ID dell'utente che richiede il comando.
     * @param data Dati aggiuntivi richiesti per l'esecuzione del comando.
     * @return La risposta con lo stato del tavolo dopo l'esecuzione del comando.
     */
    @Override
    public TavoloStatusResponse execute(Long userId, Map<String, Object> data) {
        // Ottiene il tavolo associato all'utente
        Tavolo tavolo = tavoloServiceImplementation.getTavolo(userId);

        // Distribuisce una carta all'utente
        tavolo.pescaCarta();

        // Inizializza lo stato del tavolo come "CONTINUE"
        TavoloStatus tavoloStatus = TavoloStatus.CONTINUE;
        // Controlla se l'utente ha un Blackjack (21 punti)
        if (tavolo.punteggioUtente() == 21) {
            tavoloStatus = TavoloStatus.PLAYER_WIN;
        } else if (tavolo.punteggioUtente() > 21) {
            // Controlla se l'utente ha superato i 21 punti
            tavoloStatus = TavoloStatus.PLAYER_LOSE;
        }

        // Ottiene la risposta sullo stato del tavolo
        TavoloStatusResponse tavoloStatusResponse = tavoloServiceImplementation.getTavoloStatusResponse(tavolo, tavoloStatus);
        if (tavoloStatusResponse.getTavoloStatus() != TavoloStatus.CONTINUE) {
            // Se il gioco non continua, processa la vittoria/perdita e pulisce il tavolo
            tavoloServiceImplementation.processWin(tavolo, tavoloStatus);
            tavolo.clear();
        }

        // Ritorna la risposta con lo stato del tavolo
        return tavoloStatusResponse;
    }
}