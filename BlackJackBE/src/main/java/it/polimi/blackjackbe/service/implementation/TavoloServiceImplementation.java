package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.builder.ManoBuilder;
import it.polimi.blackjackbe.dto.request.EndTavoloRequest;
import it.polimi.blackjackbe.dto.response.CartaResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.model.*;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.TavoloRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.service.definition.TavoloService;
import it.polimi.blackjackbe.singleton.SingletonTavoli;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service per gestire tutte le operazioni relative ai tavoli.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TavoloServiceImplementation implements TavoloService {

    private final TavoloRepository tavoloRepository;
    private final UserRepository userRepository;
    private final ManoRepository manoRepository;

    /**
     * Inizializza un nuovo tavolo per l'utente specificato.
     *
     * @param tipoTavolo Tipo del tavolo da inizializzare.
     * @param userId ID dell'utente che inizializza il tavolo.
     * @throws BadRequestException se l'ID dell'utente o il tipo del tavolo non sono validi.
     */
    @Override
    public void init(String tipoTavolo, Long userId) {
        // Verifica che l'ID dell'utente sia valido.
        if (userId == null || userId <= 0) {
            throw new BadRequestException("User id is required");
        }

        // Verifica che il tipo del tavolo sia valido.
        if (tipoTavolo == null || tipoTavolo.isEmpty()) {
            throw new BadRequestException("Table type is required");
        }

        // Controlla se l'utente esiste nel database.
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        // Crea un nuovo tavolo per l'utente e salva nel database.
        Tavolo tavolo = SingletonTavoli.getInstance().createTable(user.get(), TipoTavolo.valueOf(tipoTavolo));
        tavoloRepository.save(tavolo);

        // Inizializza le carte per il tavolo.
        tavolo.initCarte();
    }

    /**
     * Termina il tavolo per l'utente specificato.
     *
     * @param userId ID dell'utente che termina il tavolo.
     * @param request Richiesta contenente i dati per terminare il tavolo.
     * @throws BadRequestException se l'utente o il tavolo non vengono trovati.
     */
    @Override
    public void end(Long userId, EndTavoloRequest request) {
        // Controlla se l'utente esiste nel database.
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        // Controlla se il tavolo esiste nel database.
        Optional<Tavolo> tavolo = tavoloRepository.findByPlayer(user.get());
        if (tavolo.isEmpty()) {
            throw new BadRequestException("Table not found");
        }

        // Salva lo stato del tavolo e termina la sessione.
        tavoloRepository.save(tavolo.get());
        tavolo.get().end();
    }

    /**
     * Fornisce lo stato corrente del tavolo e altre informazioni rilevanti.
     *
     * @param tavolo Tavolo di cui ottenere lo stato.
     * @param tavoloStatus Stato attuale del tavolo.
     * @return Un oggetto TavoloStatusResponse contenente le informazioni sullo stato del tavolo.
     */
    public TavoloStatusResponse getTavoloStatusResponse(Tavolo tavolo, TavoloStatus tavoloStatus) {
        // Recupera l'utente dal repository.
        User user = userRepository.findById(tavolo.getPlayer().getUserId()).get();

        // Costruisce e restituisce la risposta con le informazioni sullo stato del tavolo.
        return new TavoloStatusResponse(
                tavolo.getCarteSingolaManoPlayer().stream().map(carta -> new CartaResponse(carta.getSeme(), carta.getValore(), carta.getPunteggio(), carta.getOrder())).toList(),
                tavolo.punteggioUtente(),
                tavolo.getCarteSingolaManoDealer().stream().map(carta -> new CartaResponse(carta.getSeme(), carta.getValore(), carta.getPunteggio(), carta.getOrder())).toList(),
                tavolo.punteggioDealer(),
                tavoloStatus,
                user.getSaldo(),
                tavolo.getTotalWinning()
        );
    }

    /**
     * Recupera il tavolo associato all'utente specificato.
     *
     * @param userId ID dell'utente di cui recuperare il tavolo.
     * @return Un oggetto Tavolo associato all'utente specificato.
     * @throws BadRequestException se l'utente non viene trovato.
     * @throws IllegalStateException se la mano non è stata distribuita.
     */
    public Tavolo getTavolo(Long userId) {
        // Controlla se l'utente esiste nel database.
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        // Recupera il tavolo associato all'utente.
        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user.get());

        // Verifica se la mano è stata distribuita correttamente.
        if (tavolo.getCarteSingolaManoPlayer().size() < 2) {
            throw new IllegalStateException("The deal was not made");
        }
        return tavolo;
    }

    /**
     * Processa la vincita del giocatore per il tavolo specificato.
     *
     * @param tavolo Tavolo di cui processare la vincita.
     * @param tavoloStatus Stato del tavolo dopo la partita.
     */
    public void processWin(Tavolo tavolo, TavoloStatus tavoloStatus) {
        processWin(tavolo, tavoloStatus, false);
    }

    /**
     * Processa la vincita del giocatore per il tavolo specificato, con un'opzione per il blackjack.
     *
     * @param tavolo Tavolo di cui processare la vincita.
     * @param tavoloStatus Stato del tavolo dopo la partita.
     * @param blackJack Indica se il giocatore ha vinto con un blackjack.
     */
    public void processWin(Tavolo tavolo, TavoloStatus tavoloStatus, boolean blackJack) {
        // Recupera l'utente associato al tavolo e l'amministratore dal repository.
        User user = userRepository.findById(tavolo.getPlayer().getUserId()).get();
        User admin = userRepository.findByRuolo(Ruolo.ADMIN).get();
        Double importo = 0.0;

        // Calcola le vincite e aggiorna i saldi in base allo stato del tavolo.
        if (tavoloStatus == TavoloStatus.PLAYER_WIN) {
            // Se il giocatore ha vinto, calcola la vincita in base alla puntata dell'utente.
            // Se il giocatore ha vinto con un blackjack, la vincita è 2.5 volte la puntata, altrimenti è 2 volte.
            Double vincita = tavolo.getPlotUser() * (blackJack ? 2.5 : 2);
            user.setSaldo(user.getSaldo() + vincita); // Aggiunge la vincita al saldo dell'utente.
            admin.setSaldo(admin.getSaldo() - vincita); // Sottrae la vincita dal saldo dell'amministratore.
            tavolo.setTotalWinning(tavolo.getTotalWinning() + vincita); // Aggiorna le vincite totali del tavolo.
            importo = -vincita / 2; // Imposta l'importo per la registrazione della mano.
        } else if (tavoloStatus == TavoloStatus.PLAYER_LOSE) {
            // Se il giocatore ha perso, la puntata dell'utente viene aggiunta al saldo dell'amministratore.
            admin.setSaldo(admin.getSaldo() + tavolo.getPlotUser());
            tavolo.setTotalWinning(tavolo.getTotalWinning() - tavolo.getPlotUser()); // Aggiorna le vincite totali del tavolo.
            importo = tavolo.getPlotUser(); // Imposta l'importo per la registrazione della mano.
        } else if (tavoloStatus == TavoloStatus.DRAW) {
            // Se la partita è in pareggio, la puntata dell'utente viene restituita al saldo dell'utente.
            user.setSaldo(user.getSaldo() + tavolo.getPlotUser());
        }

        // Registra la mano nel database.
        Mano mano = new ManoBuilder()
                .tavolo(tavolo)
                .dataMano(LocalDateTime.now())
                .importo(importo)
                .build();
        manoRepository.save(mano);
    }
}
