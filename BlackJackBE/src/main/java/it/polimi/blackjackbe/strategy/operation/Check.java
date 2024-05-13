package it.polimi.blackjackbe.strategy.operation;

import it.polimi.blackjackbe.model.Carta;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.strategy.SceltaStrategy;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
public class Check implements SceltaStrategy {
    @Override
    public Carta esegui(User user) {
        System.out.println("Controllo");
        return null;
    }

    //TODO: chiamare il singleton per controllare chi vince o perde
}
