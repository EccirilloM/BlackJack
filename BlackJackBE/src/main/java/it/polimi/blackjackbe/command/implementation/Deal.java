package it.polimi.blackjackbe.command.implementation;


import it.polimi.blackjackbe.command.Command;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import it.polimi.blackjackbe.singleton.SingletonTavoli;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Classe che implementa il comando di "Deal" per il gioco del Blackjack.
 * Questa classe esegue l'azione di distribuire le carte all'utente e al dealer.
 */
@AllArgsConstructor
@Component
@Transactional
public class Deal extends Command {
    private final UserRepository userRepository;
    private final TavoloServiceImplementation tavoloServiceImplementation;

    /**
     * Esegue il comando di Deal.
     * @param userId ID dell'utente che richiede il comando.
     * @param data Dati aggiuntivi richiesti per l'esecuzione del comando, inclusi il plot.
     * @return La risposta con lo stato del tavolo dopo l'esecuzione del comando.
     */
    @Override
    public TavoloStatusResponse execute(Long userId, Map<String, Object> data) {
        double plot = 0.0;
        try {
            // Estrae il valore del plot dai dati forniti
            plot = java.lang.Double.valueOf(data.get("plot").toString());
        } catch (NumberFormatException | NullPointerException e) {
            // Gestisce il caso in cui il plot non sia valido o sia nullo
            throw new BadRequestException("Plot is invalid");
        }

        // Cerca l'utente nel repository
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            // Se l'utente non viene trovato, lancia un'eccezione
            throw new BadRequestException("User not found");
        }

        // Controlla se il saldo dell'utente è sufficiente per il plot
        if (user.get().getSaldo() < plot) {
            // Se il saldo è insufficiente, lancia un'eccezione
            throw new IllegalStateException("The plot is higher than the balance");
        }

        // Aggiorna il saldo dell'utente sottraendo il plot
        user.get().setSaldo(user.get().getSaldo() - plot);

        // Ottiene il tavolo associato all'utente dal singleton
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user.get());
        // Imposta il plot dell'utente sul tavolo
        tavolo.setPlotUser(plot);

        // Pesca una carta per il dealer
        tavolo.pescaDealer();
        // Pesca due carte per l'utente
        tavolo.pescaCarta();
        tavolo.pescaCarta();

        // Inizializza lo stato del tavolo come "CONTINUE"
        TavoloStatus tavoloStatus = TavoloStatus.CONTINUE;
        // Controlla se l'utente ha un Blackjack (21 punti)
        if (tavolo.punteggioUtente() == 21) {
            // Se l'utente ha un Blackjack, imposta lo stato del tavolo su "PLAYER_WIN"
            tavoloStatus = TavoloStatus.PLAYER_WIN;
            // Processa la vittoria dell'utente
            tavoloServiceImplementation.processWin(tavolo, tavoloStatus, true);
        }

        // Ottiene la risposta sullo stato del tavolo
        TavoloStatusResponse tavoloStatusResponse = tavoloServiceImplementation.getTavoloStatusResponse(tavolo, tavoloStatus);
        // Se il gioco non continua, pulisce il tavolo
        if (tavoloStatusResponse.getTavoloStatus() != TavoloStatus.CONTINUE) {
            tavolo.clear();
        }

        // Ritorna la risposta con lo stato del tavolo
        return tavoloStatusResponse;
    }
}
