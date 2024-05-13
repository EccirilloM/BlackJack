package it.polimi.blackjackbe.strategy.context;

import it.polimi.blackjackbe.model.Carta;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.strategy.SceltaStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class StrategyManager {

    public Carta executeStrategy(User user, SceltaStrategy strategy) {
        return strategy.esegui(user);
    }
}
