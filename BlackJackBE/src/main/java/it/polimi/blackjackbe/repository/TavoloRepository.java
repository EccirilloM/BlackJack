package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia responsabile della comunicazione con il database per il modello {@link Tavolo}.
 * Estende l'interfaccia generica JpaRepository, specificando il tipo della classe e il tipo dell'ID.
 */
public interface TavoloRepository extends JpaRepository<Tavolo, Long> {

    /**
     * Recupera il tavolo associato a un determinato giocatore.
     * @param player Giocatore per cui recuperare il tavolo.
     * @return Un Optional contenente il tavolo associato al giocatore specificato, se presente.
     */
    Optional<Tavolo> findByPlayer(User player);

    /**
     * Recupera tutti i tavoli associati a un determinato giocatore.
     * @param player Giocatore per cui recuperare il tavolo.
     * @return Lista contenente tutti i tavoli associati al giocatore specificato, se presente.
     */
    List<Tavolo> findAllByPlayer(User player);
}
