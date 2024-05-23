package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Mano;
import it.polimi.blackjackbe.model.Tabacchi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManoRepository extends JpaRepository<Mano, Long> {
    List<Mano> findAll();
}
