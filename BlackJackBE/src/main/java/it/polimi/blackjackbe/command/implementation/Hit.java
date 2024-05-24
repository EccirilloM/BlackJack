package it.polimi.blackjackbe.command.implementation;

import it.polimi.blackjackbe.command.Command;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.TavoloRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import it.polimi.blackjackbe.strategy.context.StrategyManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Transactional
@AllArgsConstructor
public class Hit extends Command {
    private final TavoloRepository tavoloRepository;
    private final StrategyManager strategyManager;
    private final UserRepository userRepository;
    private final ManoRepository manoRepository;
    private final TavoloServiceImplementation tavoloServiceImplementation;

    @Override
    public TavoloStatusResponse execute(Long userId, Map<String, Object> data) {
        Tavolo tavolo = tavoloServiceImplementation.getTavolo(userId);

        tavolo.pescaCarta();
        TavoloStatus tavoloStatus = TavoloStatus.CONTINUE;
        if(tavolo.punteggioUtente()==21){
            tavoloStatus = TavoloStatus.PLAYER_WIN;
        } else if (tavolo.punteggioUtente()>21) {
            tavoloStatus = TavoloStatus.PLAYER_LOSE;
        }
        TavoloStatusResponse tavoloStatusResponse = tavoloServiceImplementation.getTavoloStatusResponse(tavolo, tavoloStatus);
        if(tavoloStatusResponse.getTavoloStatus() != TavoloStatus.CONTINUE){
            tavoloServiceImplementation.processWin(tavolo, tavoloStatus);
            tavolo.clear();
        }
        return tavoloStatusResponse;
    }
}
