package it.polimi.blackjackbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartaResponse {
    private String seme;
    private String valore;
    private int punteggio;
    private int order;
}
