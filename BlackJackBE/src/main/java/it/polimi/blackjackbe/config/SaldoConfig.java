package it.polimi.blackjackbe.config;

import it.polimi.blackjackbe.service.definition.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Configurazione per la gestione del saldo degli utenti.
 * Grazie all'annotazione "@Configuration", il contenuto di questa classe viene eseguito appena il server viene avviato.
 * L'annotazione "@EnableScheduling" abilita la schedulazione delle attività pianificate.
 */
@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class SaldoConfig {
    private final UserService userService;

    /**
     * Metodo schedulato per ricaricare il saldo degli utenti.
     * Questo metodo viene eseguito ogni 5 giorni.
     */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24 * 5) // Ogni 5 giorni (1000 millisecondi * 60 secondi * 60 minuti * 24 ore * 5 giorni)
    public void ricaricaSaldo() {
        // Ricarica il saldo dell'utente chiamando il metodo del servizio utente
        userService.ricaricaSaldo();
    }
}