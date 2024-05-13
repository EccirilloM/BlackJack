package it.polimi.blackjackbe.strategy;

import it.polimi.blackjackbe.model.Carta;
import it.polimi.blackjackbe.model.User;

public interface SceltaStrategy {


    Carta esegui(User user);
}
