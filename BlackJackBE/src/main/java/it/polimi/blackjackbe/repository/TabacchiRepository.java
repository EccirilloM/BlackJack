package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaccia responsabile della comunicazione con il database per il modello {@link Tabacchi}.
 * Estende l'interfaccia generica JpaRepository, specificando il tipo della classe e il tipo dell'ID.
 */
public interface TabacchiRepository extends JpaRepository<Tabacchi, Long> {

    /**
     * Recupera tutti i tabacchi presenti nel database.
     * @return Lista di tutti i tabacchi.
     */
    List<Tabacchi> findAll();

    /**
     * Recupera tutti i tabacchi gestiti da un determinato economo.
     * @param economo Economo per cui recuperare i tabacchi.
     * @return Lista di tabacchi gestiti dall'economo specificato.
     */
    List<Tabacchi> findAllByEconomo(User economo);
}
