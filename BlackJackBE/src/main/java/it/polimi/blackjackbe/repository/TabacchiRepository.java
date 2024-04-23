package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TabacchiRepository extends JpaRepository<Tabacchi, Long> {
    List<Tabacchi> findAll();

}
