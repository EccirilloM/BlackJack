package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.RichiestaRicaricaRequest;
import it.polimi.blackjackbe.dto.response.GetAllRichiesteRicaricaSaldoResponse;

import java.util.List;

/**
 * Interfaccia che contiene le firme dei metodi del service che gestisce le operazioni relative alle ricariche.
 * Implementazione -> {@link it.polimi.blackjackbe.service.implementation.RicaricaServiceImplementation}.
 */
public interface RicaricaService {

    /**
     * Richiede una ricarica per un determinato utente.
     * @param userId ID dell'utente che richiede la ricarica.
     * @param request Richiesta di ricarica contenente i dettagli della ricarica.
     */
    void richiediRicarica(Long userId, RichiestaRicaricaRequest request);

    /**
     * Recupera tutte le richieste di ricarica associate a un determinato economo.
     * @param userId ID dell'economo per cui recuperare le richieste di ricarica.
     * @return Lista di risposte contenenti tutte le richieste di ricarica dell'economo specificato.
     */
    List<GetAllRichiesteRicaricaSaldoResponse> getAllRichiesteRicarica(Long userId);

    /**
     * Accetta una richiesta di ricarica.
     * @param ricaricaId ID della ricarica da accettare.
     * @param playerId ID del giocatore per cui accettare la ricarica.
     */
    void accettaRicarica(Long ricaricaId, Long playerId);

    /**
     * Rifiuta una richiesta di ricarica.
     * @param ricaricaId ID della ricarica da rifiutare.
     */
    void rifiutaRicarica(Long ricaricaId);
}
