package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.MessaggioRequest;
import it.polimi.blackjackbe.dto.response.GetAllMessagesByTipoTavoloResponse;
import it.polimi.blackjackbe.dto.response.GetAllRichiesteRicaricaSaldoResponse;
import it.polimi.blackjackbe.model.TipoTavolo;

import java.util.List;

/**
 * Interfaccia che contiene le firme dei metodi del service che gestisce le operazioni relative ai messaggi.
 * Implementazione -> {@link it.polimi.blackjackbe.service.implementation.MessaggioServiceImplementation}.
 */
public interface MessaggioService {

    /**
     * Recupera tutti i messaggi associati a un determinato tipo di tavolo.
     * @param tipoTavolo Il tipo di tavolo per cui recuperare i messaggi.
     * @return Lista di risposte contenenti tutti i messaggi per il tipo di tavolo specificato.
     */
    List<GetAllMessagesByTipoTavoloResponse> getAllMessagesByTipoTavolo(String tipoTavolo);

    /**
     * Invia un messaggio basato sulla richiesta fornita.
     * @param messaggioRequest La richiesta contenente i dettagli del messaggio da inviare.
     */
    void inviaMessaggio(MessaggioRequest messaggioRequest);
}
