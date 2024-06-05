package it.polimi.blackjackbe.command.implementation;

import it.polimi.blackjackbe.command.Command;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Classe che implementa il comando di "Double" per il gioco del Blackjack.
 * Questa classe esegue l'azione di raddoppiare la puntata dell'utente e distribuisce una carta aggiuntiva.
 */
@Component
@AllArgsConstructor
@Transactional
public class Double extends Command {
    private final TavoloServiceImplementation tavoloServiceImplementation;
    private final Command hit;
    private final Command stay;

    /**
     * Esegue il comando di Double.
     * @param userId ID dell'utente che richiede il comando.
     * @param data Dati aggiuntivi richiesti per l'esecuzione del comando.
     * @return La risposta con lo stato del tavolo dopo l'esecuzione del comando.
     */
    @Override
    public TavoloStatusResponse execute(Long userId, Map<String, Object> data) {
        // Ottiene il tavolo associato all'utente
        Tavolo tavolo = tavoloServiceImplementation.getTavolo(userId);
        // Ottiene l'utente dal tavolo
        User user = tavolo.getPlayer();

        // Riduce il saldo dell'utente del valore della puntata attuale
        user.setSaldo(user.getSaldo() - tavolo.getPlotUser());

        // Raddoppia la puntata dell'utente
        tavolo.setPlotUser(tavolo.getPlotUser() * 2);

        // Esegue il comando "hit" (distribuisce una carta aggiuntiva)
        TavoloStatusResponse tavoloStatusResponse = hit.execute(userId, data);
        if (tavoloStatusResponse.getTavoloStatus() != TavoloStatus.CONTINUE) {
            // Se lo stato del tavolo non è "CONTINUE", ritorna la risposta corrente
            return tavoloStatusResponse;
        } else {
            // Altrimenti, esegue il comando "stay" (il giocatore rimane con le carte attuali)
            return stay.execute(userId, data);
        }
    }
}