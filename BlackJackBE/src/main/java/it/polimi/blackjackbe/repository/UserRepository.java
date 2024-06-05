package it.polimi.blackjackbe.repository;

import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia responsabile della comunicazione con il database per il modello {@link User}.
 * Estende l'interfaccia generica JpaRepository, specificando il tipo della classe e il tipo dell'ID.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recupera un utente in base al nome utente.
     * @param username Nome utente per cui recuperare l'utente.
     * @return Un Optional contenente l'utente associato al nome utente specificato, se presente.
     */
    Optional<User> findByUsername(String username);

    /**
     * Recupera un utente in base all'ID utente.
     * @param userId ID utente per cui recuperare l'utente.
     * @return Un Optional contenente l'utente associato all'ID utente specificato, se presente.
     */
    Optional<User> findByUserId(Long userId);

    /**
     * Recupera tutti gli utenti con un determinato ruolo.
     * @param ruolo Ruolo per cui recuperare gli utenti.
     * @return Lista di utenti con il ruolo specificato.
     */
    List<User> findAllByRuolo(Ruolo ruolo);

    /**
     * Recupera un utente con un determinato ruolo.
     * @param ruolo Ruolo per cui recuperare l'utente.
     * @return Un Optional contenente l'utente con il ruolo specificato, se presente.
     */
    Optional<User> findByRuolo(Ruolo ruolo);
}
