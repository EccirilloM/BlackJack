package it.polimi.blackjackbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreaTabacchiRequest {
    private String nomeTabacchi;
    private Float lat;
    private Float lng;
    private Long economoId;
}
