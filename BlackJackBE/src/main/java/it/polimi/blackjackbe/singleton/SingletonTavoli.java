package it.polimi.blackjackbe.singleton;

import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;

import java.util.HashMap;
import java.util.Map;

public class SingletonTavoli {
    private static final SingletonTavoli instance = new SingletonTavoli();

    private SingletonTavoli() {
    }

    public static SingletonTavoli getInstance() {
        return instance;
    }

    private Map<Long, Tavolo> tavoliAttivi=new HashMap<>();

    public Tavolo createTable(User user, TipoTavolo tipoTavolo){
        Tavolo tavolo=new Tavolo();
        tavolo.setTipoTavolo(tipoTavolo);
        tavolo.setPlayer(user);
        tavoliAttivi.put(user.getUserId(),tavolo);
        return tavoliAttivi.get(user.getUserId());
    }

    public Tavolo getTable(User user){
        return tavoliAttivi.get(user.getUserId());
    }

    //TODO: implementare i metodi per checkare chi vince o perde


}
