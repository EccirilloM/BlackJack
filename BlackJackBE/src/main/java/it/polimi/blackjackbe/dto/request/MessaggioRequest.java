package it.polimi.blackjackbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessaggioRequest {
    private String testo;
    private Long mittenteId;
    private String tipoTavolo;
}
