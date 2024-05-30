package it.polimi.blackjackbe.dto.response;

import it.polimi.blackjackbe.model.TavoloStatus;
import lombok.*;

import java.util.List;

@Data
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

