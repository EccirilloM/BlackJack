package it.polimi.blackjackbe.strategy.operation;

import it.polimi.blackjackbe.strategy.SceltaStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Raddoppia implements SceltaStrategy {
    @Override
    public void esegui() {
        System.out.println("Raddoppio");
    }
}
