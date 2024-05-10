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

    public void createTable(User user, TipoTavolo tipoTavolo){
        Tavolo tavolo=new Tavolo();
        tavolo.setTipoTavolo(tipoTavolo);
        tavoliAttivi.put(user.getUserId(),tavolo);
    }

    private Tavolo getTable(User user){
        return tavoliAttivi.get(user.getUserId());
    }

    //TODO: implementare i metodi per checkare chi vince o perde


}
