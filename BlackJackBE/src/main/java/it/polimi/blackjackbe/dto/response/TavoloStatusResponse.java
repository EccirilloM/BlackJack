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
    private Double saldo;
    private Double winning;

    public TavoloStatusResponse(List<CartaResponse> cartePlayer, int punteggioPlayer, List<CartaResponse> carteDealer, int punteggioDealer, TavoloStatus tavoloStatus, Double saldo, Double winning) {
        this.cartePlayer = cartePlayer;
        this.punteggioPlayer = punteggioPlayer;
        this.carteDealer = carteDealer;
        this.punteggioDealer = punteggioDealer;
        this.tavoloStatus = tavoloStatus;
        this.saldo = saldo;
        this.winning = winning;
    }
}

