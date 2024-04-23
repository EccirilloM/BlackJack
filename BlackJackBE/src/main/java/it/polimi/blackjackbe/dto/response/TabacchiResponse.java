package it.polimi.blackjackbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TabacchiResponse {
    private Long tabacchiId;

    private String nomeTabacchi;

    private Double lat;

    private Double lng;

    private Long userId;
}
