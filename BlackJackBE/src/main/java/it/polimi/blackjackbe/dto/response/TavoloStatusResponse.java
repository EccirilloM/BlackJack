package it.polimi.blackjackbe.dto.response;

import it.polimi.blackjackbe.model.TavoloStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TavoloStatusResponse {
    private List<CartaResponse> cartePlayer;
    private int punteggioPlayer;
    private List<CartaResponse> carteDealer;
    private int punteggioDealer;
    private TavoloStatus tavoloStatus;
    private Double saldo;
    private Double winning;

}

