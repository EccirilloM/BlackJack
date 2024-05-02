package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Ricarica;
import it.polimi.blackjackbe.model.Tabacchi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RicaricaRepository extends JpaRepository<Ricarica, Long> {
    List<Ricarica> findAllByTabacchi(Tabacchi tabacchi);
}
