package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Notifica;
import it.polimi.blackjackbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaccia responsabile della comunicazione con il database per il modello {@link Notifica}.
 * Estende l'interfaccia generica JpaRepository, specificando il tipo della classe e il tipo dell'ID.
 */
public interface NotificaRepository extends JpaRepository<Notifica, Long> {

    /**
     * Recupera tutte le notifiche associate a un determinato utente.
     * @param player Utente per cui recuperare le notifiche.
     * @return Lista di notifiche associate all'utente specificato.
     */
    List<Notifica> findAllByPlayer(User player);
}
