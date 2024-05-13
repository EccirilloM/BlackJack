package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TavoloRepository extends JpaRepository<Tavolo, Long> {

    Optional<Tavolo> findByPlayer(User player);
}
