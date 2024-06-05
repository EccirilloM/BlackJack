package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.response.NotificaResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.model.Notifica;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.NotificaRepository;
import it.polimi.blackjackbe.repository.UserRepository;

import it.polimi.blackjackbe.service.definition.NotificaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del service che gestisce le operazioni relative alle notifiche.
 */
@Service
@RequiredArgsConstructor
public class NotificaServiceImplementation implements NotificaService {

    private final NotificaRepository notificaRepository;
    private final UserRepository userRepository;

    /**
     * Recupera tutte le notifiche associate a un determinato utente.
     * @param userId ID dell'utente per cui recuperare le notifiche.
     * @return Lista di risposte contenenti tutte le notifiche dell'utente specificato.
     */
    @Override
    public List<NotificaResponse> getAllByUserId(Long userId) {

        // Verifica che l'ID utente sia valido.
        if (userId < 1) {
            throw new BadRequestException("Id non valido");
        }

        // Prende dal database l'utente con l'ID passato come parametro.
        Optional<User> utenteExists = userRepository.findById(userId);

        // Se l'utente non viene trovato, lancia un'eccezione.
        if (utenteExists.isEmpty()) {
            throw new BadRequestException("Utente non trovato");
        }

        // Prende dal database tutte le notifiche dell'utente specificato.
        List<Notifica> notifiche = notificaRepository.findAllByPlayer(utenteExists.get());

        // Inizializza la variabile di risposta.
        List<NotificaResponse> response = new ArrayList<>();

        // Per ogni notifica, crea un oggetto di risposta e lo aggiunge alla lista di risposta.
        for (Notifica notifica : notifiche) {
            response.add(new NotificaResponse(notifica.getData(), notifica.getTesto()));
        }

        return response;
    }
}
