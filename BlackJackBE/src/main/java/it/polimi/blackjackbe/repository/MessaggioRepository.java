package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Messaggio;
import it.polimi.blackjackbe.model.Ricarica;
import it.polimi.blackjackbe.model.TipoTavolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessaggioRepository extends JpaRepository<Messaggio, Long> {
    List<Messaggio> findAllByTipoTavolo(TipoTavolo tipoTavolo);
}
