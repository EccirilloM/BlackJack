package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.CreaTabacchiRequest;
import it.polimi.blackjackbe.dto.response.TabacchiResponse;
import it.polimi.blackjackbe.dto.response.UserResponse;

import java.util.List;

public interface TabacchiService {
    void creaTabacchi(CreaTabacchiRequest request);

    List<TabacchiResponse> getAllTabacchi();

    void deleteTabacchi(Long tabacchiId);
}
