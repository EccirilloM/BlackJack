package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.response.GetAllManiResponse;
import it.polimi.blackjackbe.dto.response.GetAllMessagesByTipoTavoloResponse;

import java.util.List;

public interface ManoService {
    List<GetAllManiResponse> getAllMani();
}
