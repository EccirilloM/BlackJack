package it.polimi.blackjackbe.strategy.operation;

import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.model.Carta;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.singleton.SingletonTavoli;
import it.polimi.blackjackbe.strategy.SceltaStrategy;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@NoArgsConstructor
public class ChiediCarta implements SceltaStrategy {

    @Override
    public Carta esegui(User user) {
        return SingletonTavoli.getInstance().getTable(user).pescaCarta();
    }
}
