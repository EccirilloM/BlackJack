package it.polimi.blackjackbe.strategy.context;

import it.polimi.blackjackbe.strategy.SceltaStrategy;
import lombok.RequiredArgsConstructor;

public class StrategyManager {
    private SceltaStrategy strategy;

    public void executeStrategy() {
        strategy.esegui();
    }

    public void setStrategy(SceltaStrategy strategy) {
        this.strategy = strategy;
    }
}
