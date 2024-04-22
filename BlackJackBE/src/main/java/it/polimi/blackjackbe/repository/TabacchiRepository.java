package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TabacchiRepository extends JpaRepository<Tabacchi, Long> {

}
