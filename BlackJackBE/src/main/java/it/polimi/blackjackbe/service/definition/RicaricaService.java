package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.RichiestaRicaricaRequest;
import it.polimi.blackjackbe.dto.response.GetAllRichiesteRicaricaSaldoResponse;

import java.util.List;

public interface RicaricaService {
    void richiediRicarica(Long userId, RichiestaRicaricaRequest request);

    List<GetAllRichiesteRicaricaSaldoResponse> getAllRichiesteRicarica(Long userId);

    void accettaRicarica(Long ricaricaId, Long playerId);

    void rifiutaRicarica(Long ricaricaId);
}
