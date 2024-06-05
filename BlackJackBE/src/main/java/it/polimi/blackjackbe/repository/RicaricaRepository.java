package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Ricarica;
import it.polimi.blackjackbe.model.Tabacchi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaccia responsabile della comunicazione con il database per il modello {@link Ricarica}.
 * Estende l'interfaccia generica JpaRepository, specificando il tipo della classe e il tipo dell'ID.
 */
public interface RicaricaRepository extends JpaRepository<Ricarica, Long> {

    /**
     * Recupera tutte le ricariche associate a un determinato tabacchi.
     * @param tabacchi Tabacchi per cui recuperare le ricariche.
     * @return Lista di ricariche associate al tabacchi specificato.
     */
    List<Ricarica> findAllByTabacchi(Tabacchi tabacchi);
}
