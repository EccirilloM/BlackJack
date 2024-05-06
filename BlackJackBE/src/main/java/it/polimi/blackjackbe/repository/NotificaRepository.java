package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Notifica;
import it.polimi.blackjackbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificaRepository extends JpaRepository<Notifica, Long> {

    List<Notifica> findAllByPlayer(User player);
}
