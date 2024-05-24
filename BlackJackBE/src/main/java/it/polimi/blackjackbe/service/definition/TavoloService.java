package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.EndTavoloRequest;
import it.polimi.blackjackbe.dto.response.CartaResponse;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;

public interface TavoloService {

    void init(String tipoTavolo, Long userId);

    void end(Long userId, EndTavoloRequest request);

}
