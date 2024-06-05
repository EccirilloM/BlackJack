package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.CreaTabacchiRequest;
import it.polimi.blackjackbe.dto.response.TabacchiResponse;
import it.polimi.blackjackbe.dto.response.UserResponse;

import java.util.List;

/**
 * Interfaccia che contiene le firme dei metodi del service che gestisce le operazioni relative ai tabacchi.
 * Implementazione -> {@link it.polimi.blackjackbe.service.implementation.TabacchiServiceImplementation}.
 */
public interface TabacchiService {

    /**
     * Crea un nuovo tabacchi.
     * @param request Richiesta contenente i dettagli del tabacchi da creare.
     */
    void creaTabacchi(CreaTabacchiRequest request);

    /**
     * Recupera tutti i tabacchi presenti nel sistema.
     * @return Lista di risposte contenenti tutti i tabacchi.
     */
    List<TabacchiResponse> getAllTabacchi();

    /**
     * Elimina un tabacchi specifico.
     * @param tabacchiId ID del tabacchi da eliminare.
     */
    void deleteTabacchi(Long tabacchiId);
}
