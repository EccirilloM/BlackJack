package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Messaggio;
import it.polimi.blackjackbe.model.Ricarica;
import it.polimi.blackjackbe.model.TipoTavolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaccia responsabile della comunicazione con il database per il modello {@link Messaggio}.
 * Estende l'interfaccia generica JpaRepository, specificando il tipo della classe e il tipo dell'ID.
 */
public interface MessaggioRepository extends JpaRepository<Messaggio, Long> {

    /**
     * Recupera tutti i messaggi associati a un determinato tipo di tavolo.
     * @param tipoTavolo Tipo di tavolo per cui recuperare i messaggi.
     * @return Lista di messaggi associati al tipo di tavolo specificato.
     */
    List<Messaggio> findAllByTipoTavolo(TipoTavolo tipoTavolo);
}
