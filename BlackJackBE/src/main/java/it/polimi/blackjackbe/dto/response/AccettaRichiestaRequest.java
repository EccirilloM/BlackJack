package it.polimi.blackjackbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccettaRichiestaRequest {
    private Long richiestaId;
    private Long playerId;
}
