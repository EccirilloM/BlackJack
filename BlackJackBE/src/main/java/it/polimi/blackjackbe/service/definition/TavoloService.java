package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.EndTavoloRequest;

/**
 * Interfaccia che contiene le firme dei metodi del service che gestisce le operazioni relative ai tavoli.
 * Implementazione -> {@link it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation}.
 */
public interface TavoloService {

    /**
     * Inizializza un nuovo tavolo per l'utente specificato.
     * @param tipoTavolo Il tipo di tavolo da inizializzare.
     * @param userId ID dell'utente per cui inizializzare il tavolo.
     */
    void init(String tipoTavolo, Long userId);

    /**
     * Termina il tavolo per l'utente specificato.
     * @param userId ID dell'utente per cui terminare il tavolo.
     * @param request Richiesta contenente i dettagli per terminare il tavolo.
     */
    void end(Long userId, EndTavoloRequest request);
}
