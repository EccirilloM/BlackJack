package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.RichiestaRicaricaRequest;

public interface RicaricaService {
    void richiediRicarica(Long userId, RichiestaRicaricaRequest request);
}
