package it.polimi.blackjackbe.singleton;

import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe Singleton per la gestione dei tavoli attivi.
 * Assicura che ci sia una sola istanza della classe in esecuzione.
 */
public class SingletonTavoli {
    // Istanza statica della classe SingletonTavoli.
    private static final SingletonTavoli instance = new SingletonTavoli();

    // Mappa che contiene i tavoli attivi associati agli ID degli utenti.
    private Map<Long, Tavolo> tavoliAttivi = new HashMap<>();

    // Costruttore privato per evitare l'istanza diretta.
    private SingletonTavoli() {
    }

    /**
     * Restituisce l'unica istanza della classe SingletonTavoli.
     * @return L'istanza Singleton di SingletonTavoli.
     */
    public static SingletonTavoli getInstance() {
        return instance;
    }

    /**
     * Crea un nuovo tavolo e lo associa all'utente specificato.
     * @param user L'utente a cui verrà associato il tavolo.
     * @param tipoTavolo Il tipo di tavolo da creare.
     * @return Il tavolo creato e associato all'utente.
     */
    public Tavolo createTable(User user, TipoTavolo tipoTavolo) {
        // Crea un nuovo tavolo.
        Tavolo tavolo = new Tavolo();
        tavolo.setTipoTavolo(tipoTavolo);
        tavolo.setPlayer(user);
        // Aggiunge il tavolo alla mappa dei tavoli attivi.
        tavoliAttivi.put(user.getUserId(), tavolo);
        // Restituisce il tavolo appena creato.
        return tavoliAttivi.get(user.getUserId());
    }

    /**
     * Recupera il tavolo associato a un utente specificato.
     * @param user L'utente di cui recuperare il tavolo.
     * @return Il tavolo associato all'utente, se presente.
     */
    public Tavolo getTable(User user) {
        return tavoliAttivi.get(user.getUserId());
    }
}
