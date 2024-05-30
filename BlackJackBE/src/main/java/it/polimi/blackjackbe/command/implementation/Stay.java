package it.polimi.blackjackbe.command.implementation;

import it.polimi.blackjackbe.command.Command;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.TavoloRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@AllArgsConstructor
@Component
@Transactional
public class Stay extends Command {
    private final TavoloServiceImplementation tavoloServiceImplementation;

    @Override
    public TavoloStatusResponse execute(Long userId, Map<String, Object> data) {
        Tavolo tavolo = tavoloServiceImplementation.getTavolo(userId);

        var punteggioUtente = tavolo.punteggioUtente();

        while(tavolo.punteggioDealer() <17) {
            tavolo.pescaDealer();
        }
        var punteggioDealer = tavolo.punteggioDealer();
        TavoloStatus tavoloStatus;
        if(punteggioDealer>21){
            tavoloStatus = TavoloStatus.PLAYER_WIN;
            tavoloServiceImplementation.processWin(tavolo, tavoloStatus);
        }else{
            if(Math.abs(punteggioUtente-21) < Math.abs(punteggioDealer-21)){
                tavoloStatus = TavoloStatus.PLAYER_WIN;
                tavoloServiceImplementation.processWin(tavolo, tavoloStatus);
            }else if(Math.abs(punteggioUtente-21)== Math.abs(punteggioDealer-21)) {
                tavoloStatus = TavoloStatus.DRAW;
                tavoloServiceImplementation.processWin(tavolo, tavoloStatus);
            }else{
                tavoloStatus = TavoloStatus.PLAYER_LOSE;
                tavoloServiceImplementation.processWin(tavolo, tavoloStatus);
            }
        }
        TavoloStatusResponse tavoloStatusResponse = tavoloServiceImplementation.getTavoloStatusResponse(tavolo, tavoloStatus);
        tavolo.clear();
        return tavoloStatusResponse;
    }
}
