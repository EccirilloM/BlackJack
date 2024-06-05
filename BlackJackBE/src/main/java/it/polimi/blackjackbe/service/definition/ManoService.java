package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.response.GetAllManiResponse;
import it.polimi.blackjackbe.dto.response.GetAllMessagesByTipoTavoloResponse;

import java.util.List;

/**
 * Interfaccia che contiene le firme dei metodi del service che gestisce le operazioni relative alle mani nel gioco.
 * Implementazione -> {@link it.polimi.blackjackbe.service.implementation.ManoServiceImplementation}.
 */
public interface ManoService {

    /**
     * Recupera tutte le mani presenti nel sistema.
     * @return Lista di risposte contenenti tutte le mani.
     */
    List<GetAllManiResponse> getAllMani();

    /**
     * Recupera tutte le mani associate a un determinato utente.
     * @param userId ID dell'utente per cui recuperare le mani.
     * @return Lista di risposte contenenti tutte le mani dell'utente specificato.
     */
    List<GetAllManiResponse> getAllManiByUserId(Long userId);
}