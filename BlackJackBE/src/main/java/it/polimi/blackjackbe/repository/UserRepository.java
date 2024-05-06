package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUserId(Long userId);

    List<User> findAllByRuolo(Ruolo ruolo);

}
