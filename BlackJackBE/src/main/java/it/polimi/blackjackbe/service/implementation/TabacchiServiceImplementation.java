package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.builder.TabacchiBuilder;
import it.polimi.blackjackbe.dto.request.CreaTabacchiRequest;
import it.polimi.blackjackbe.dto.response.TabacchiResponse;
import it.polimi.blackjackbe.dto.response.UserResponse;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.TabacchiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service per gestire tutte le operazioni relative ai tabacchi.
 */
@Service
@RequiredArgsConstructor
public class TabacchiServiceImplementation implements TabacchiService {

    private final TabacchiRepository tabacchiRepository;
    private final UserRepository userRepository;

    /**
     * Crea un nuovo tabacchi con i dati forniti nella richiesta.
     *
     * @param request DTO con i dati per la creazione del tabacchi.
     * @throws IllegalArgumentException se uno dei campi forniti non è valido.
     */
    @Override
    public void creaTabacchi(CreaTabacchiRequest request) {
        // Verifica che il nome del tabacchi sia valido.
        if (request.getNomeTabacchi().isEmpty() || request.getNomeTabacchi().isBlank()) {
            throw new IllegalArgumentException("Nome tabacchi non valido");
        }

        // Verifica che la latitudine sia valida.
        if (request.getLat().isInfinite() || request.getLat().isNaN()) {
            throw new IllegalArgumentException("Latitudine non valida");
        }

        // Verifica che la longitudine sia valida.
        if (request.getLng().isInfinite() || request.getLng().isNaN()) {
            throw new IllegalArgumentException("Longitudine non valida");
        }

        // Verifica che l'ID dell'economo sia valido.
        if (request.getEconomoId() < 1) {
            throw new IllegalArgumentException("Id economo non valido");
        }

        // Controlla se l'economo esiste nel database.
        Optional<User> economo = userRepository.findById(request.getEconomoId());
        if (economo.isEmpty()) {
            throw new IllegalArgumentException("Economo non trovato");
        }

        // Crea un nuovo tabacchi con i dati forniti e salva nel database.
        Tabacchi tabacchi = new TabacchiBuilder()
                .nome(request.getNomeTabacchi())
                .lng(Double.valueOf(request.getLng()))
                .lat(Double.valueOf(request.getLat()))
                .economo(economo.get())
                .build();
        tabacchiRepository.save(tabacchi);
    }

    /**
     * Recupera tutti i tabacchi dal database.
     *
     * @return Lista di risposte contenenti tutti i tabacchi.
     */
    @Override
    public List<TabacchiResponse> getAllTabacchi() {
        // Prende dal database tutti i tabacchi.
        List<Tabacchi> tabacchis = tabacchiRepository.findAll();

        // Inizializza la variabile di risposta.
        List<TabacchiResponse> response = new ArrayList<>();

        // Per ogni tabacchi, crea un oggetto di risposta e lo aggiunge alla lista di risposta.
        for (Tabacchi tabacchi : tabacchis) {
            response.add(new TabacchiResponse(
                    tabacchi.getTabacchiId(),
                    tabacchi.getNome(),
                    tabacchi.getLat(),
                    tabacchi.getLng(),
                    tabacchi.getEconomo().getUserId()
            ));
        }

        return response;
    }

    /**
     * Elimina un tabacchi dal database.
     *
     * @param tabacchiId ID del tabacchi da eliminare.
     * @throws NotFoundException se il tabacchi non viene trovato.
     */
    @Override
    public void deleteTabacchi(Long tabacchiId) {
        // Controlla se il tabacchi esiste nel database.
        Optional<Tabacchi> tabacchi = tabacchiRepository.findById(tabacchiId);
        if (tabacchi.isEmpty()) {
            throw new NotFoundException("Tabacchi non trovato");
        }

        // Elimina il tabacchi dal database.
        tabacchiRepository.delete(tabacchi.get());
    }
}
