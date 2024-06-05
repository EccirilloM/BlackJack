package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Mano;
import it.polimi.blackjackbe.model.Tabacchi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaccia responsabile della comunicazione con il database per il modello {@link Mano}.
 * Estende l'interfaccia generica JpaRepository, specificando il tipo della classe e il tipo dell'ID.
 */
public interface ManoRepository extends JpaRepository<Mano, Long> {

    /**
     * Recupera tutte le mani presenti nel database.
     * @return Lista di tutte le mani.
     */
    List<Mano> findAll();
}
