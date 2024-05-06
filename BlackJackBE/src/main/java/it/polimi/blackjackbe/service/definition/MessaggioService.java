package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.MessaggioRequest;
import it.polimi.blackjackbe.dto.response.GetAllMessagesByTipoTavoloResponse;
import it.polimi.blackjackbe.dto.response.GetAllRichiesteRicaricaSaldoResponse;
import it.polimi.blackjackbe.model.TipoTavolo;

import java.util.List;

public interface MessaggioService {
    List<GetAllMessagesByTipoTavoloResponse> getAllMessagesByTipoTavolo(String tipoTavolo);

    void inviaMessaggio(MessaggioRequest messaggioRequest);
}
