package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.response.NotificaResponse;

import java.util.List;

public interface NotificaService {

    List<NotificaResponse> getAllByUserId(Long userId);
}
