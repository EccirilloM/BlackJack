package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.response.NotificaResponse;

import java.util.List;

/**
 * Interfaccia che contiene le firme dei metodi del service che gestisce le operazioni relative alle notifiche.
 * Implementazione -> {@link it.polimi.blackjackbe.service.implementation.NotificaServiceImplementation}.
 */
public interface NotificaService {

    /**
     * Recupera tutte le notifiche associate a un determinato utente.
     * @param userId ID dell'utente per cui recuperare le notifiche.
     * @return Lista di risposte contenenti tutte le notifiche dell'utente specificato.
     */
    List<NotificaResponse> getAllByUserId(Long userId);
}
