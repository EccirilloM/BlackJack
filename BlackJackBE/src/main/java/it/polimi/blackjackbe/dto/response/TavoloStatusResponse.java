package it.polimi.blackjackbe.dto.response;

import it.polimi.blackjackbe.model.TavoloStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class TavoloStatusResponse {
    private List<CartaResponse> cartePlayer;
    private int punteggioPlayer;
    private List<CartaResponse> carteDealer;
    private int punteggioDealer;
    private TavoloStatus tavoloStatus;

    public TavoloStatusResponse(List<CartaResponse> cartePlayer, int punteggioPlayer, List<CartaResponse> carteDealer, int punteggioDealer, TavoloStatus tavoloStatus) {
        this.cartePlayer = cartePlayer;
        this.punteggioPlayer = punteggioPlayer;
        this.carteDealer = carteDealer;
        this.punteggioDealer = punteggioDealer;
        this.tavoloStatus = tavoloStatus;
    }
}

