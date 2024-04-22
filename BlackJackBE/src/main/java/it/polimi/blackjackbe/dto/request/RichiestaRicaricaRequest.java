package it.polimi.blackjackbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RichiestaRicaricaRequest {
    private Double importo;
    private Long tabacchiId;
}
