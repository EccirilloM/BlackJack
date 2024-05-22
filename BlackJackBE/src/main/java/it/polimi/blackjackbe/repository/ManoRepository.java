package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Mano;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManoRepository extends JpaRepository<Mano, Long> {
}
