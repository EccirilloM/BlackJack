package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.response.GetAllManiResponse;
import it.polimi.blackjackbe.model.Mano;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.TavoloRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.ManoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del service che gestisce le operazioni relative alle mani nel gioco.
 */
@Service
@RequiredArgsConstructor
public class ManoServiceImplementation implements ManoService {

    private final UserRepository userRepository;
    private final ManoRepository manoRepository;

    /**
     * Recupera tutte le mani presenti nel sistema.
     * @return Lista di risposte contenenti tutte le mani.
     */
    @Override
    public List<GetAllManiResponse> getAllMani() {
        // Prendo dal database tutte le mani.
        List<Mano> mani = manoRepository.findAll();
        // Inizializzo la variabile di risposta.
        List<GetAllManiResponse> response = new ArrayList<>();

        // Per ogni mano, crea un oggetto di risposta e lo aggiunge alla lista di risposta.
        for (Mano mano : mani) {
            response.add(new GetAllManiResponse(
                    mano.getManoId(),
                    mano.getTavolo().getTavoloId(),
                    mano.getTavolo().getPlayer().getUsername(),
                    mano.getDataMano(),
                    mano.getImporto()
            ));
        }
        return response;
    }

    /**
     * Recupera tutte le mani associate a un determinato utente.
     * @param userId ID dell'utente per cui recuperare le mani.
     * @return Lista di risposte contenenti tutte le mani dell'utente specificato.
     */
    @Override
    public List<GetAllManiResponse> getAllManiByUserId(Long userId) {

        // Verifica che l'ID utente non sia nullo o negativo.
        if (userId < 0L) {
            throw new IllegalArgumentException("userId non può essere null o negativo");
        }

        // Prende dal database l'utente con l'ID passato come parametro.
        Optional<User> user = userRepository.findById(userId);

        // Se l'utente non viene trovato, lancia un'eccezione.
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Utente non trovato");
        }

        // Prende dal database tutte le mani.
        List<Mano> mani = manoRepository.findAll();
        // Inizializza la variabile di risposta.
        List<GetAllManiResponse> response = new ArrayList<>();

        // Per ogni mano, crea un oggetto di risposta e lo aggiunge alla lista di risposta se l'utente corrisponde.
        for (Mano mano : mani) {
            if (mano.getTavolo().getPlayer().getUserId() == userId) {
                response.add(new GetAllManiResponse(
                        mano.getManoId(),
                        mano.getTavolo().getTavoloId(),
                        mano.getTavolo().getPlayer().getUsername(),
                        mano.getDataMano(),
                        mano.getImporto()
                ));
            }
        }
        return response;
    }
}
