package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.builder.NotificaBuilder;
import it.polimi.blackjackbe.builder.RicaricaBuilder;
import it.polimi.blackjackbe.dto.request.RichiestaRicaricaRequest;
import it.polimi.blackjackbe.dto.response.GetAllRichiesteRicaricaSaldoResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.*;
import it.polimi.blackjackbe.repository.NotificaRepository;
import it.polimi.blackjackbe.repository.RicaricaRepository;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.RicaricaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del service che gestisce le operazioni relative alle ricariche.
 */
@Service
@RequiredArgsConstructor

public class RicaricaServiceImplementation implements RicaricaService {
    private final RicaricaRepository ricaricaRepository;
    private final UserRepository userRepository;
    private final TabacchiRepository tabacchiRepository;
    private final NotificaRepository notificaRepository;

    /**
     * Gestisce la richiesta di ricarica saldo da parte di un utente.
     *
     * @param userId  ID dell'utente che richiede la ricarica.
     * @param request DTO con i dati della richiesta di ricarica.
     * @throws BadRequestException se l'importo o gli ID forniti non sono validi.
     * @throws NotFoundException   se l'utente o il tabacchi non vengono trovati.
     */
    @Override
    public void richiediRicarica(Long userId, RichiestaRicaricaRequest request) {
        // Verifica che l'importo della ricarica sia valido.
        if (request.getImporto() < 0) {
            throw new BadRequestException("Importo non valido");
        }

        // Verifica che l'ID dell'utente sia valido.
        if (userId < 1) {
            throw new BadRequestException("Id non Valido");
        }

        // Controlla se l'utente esiste nel database.
        Optional<User> userExists = userRepository.findByUserId(userId);
        if (userExists.isEmpty()) {
            throw new NotFoundException("Utente non trovato");
        }

        // Verifica che il ruolo dell'utente sia PLAYER.
        if (!userExists.get().getRuolo().equals(Ruolo.PLAYER)) {
            throw new BadRequestException("Ruolo non valido");
        }

        // Verifica che l'ID del tabacchi sia valido.
        if (request.getTabacchiId() < 1) {
            throw new BadRequestException("Id non Valido");
        }

        // Controlla se il tabacchi esiste nel database.
        Optional<Tabacchi> tabacchiExists = tabacchiRepository.findById(request.getTabacchiId());
        if (tabacchiExists.isEmpty()) {
            throw new NotFoundException("Tabacchi non trovato");
        }

        // Crea una nuova ricarica con i dati della richiesta e salva nel database.
        Ricarica ricarica = new RicaricaBuilder()
                .richiesta(true)
                .dataRichiesta(LocalDateTime.now())
                .importo(request.getImporto())
                .player(userExists.get())
                .tabacchi(tabacchiExists.get())
                .build();
        ricaricaRepository.save(ricarica);
    }

    /**
     * Recupera tutte le richieste di ricarica saldo per un determinato economo.
     *
     * @param userId ID dell'utente economo.
     * @return Lista di risposte contenenti tutte le richieste di ricarica.
     * @throws BadRequestException se l'ID dell'utente o il ruolo non sono validi.
     * @throws NotFoundException   se l'utente non viene trovato.
     */
    @Override
    public List<GetAllRichiesteRicaricaSaldoResponse> getAllRichiesteRicarica(Long userId) {
        // Verifica che l'ID dell'utente sia valido.
        if (userId < 1) {
            throw new BadRequestException("Id non Valido");
        }

        // Controlla se l'utente esiste nel database.
        Optional<User> userExists = userRepository.findByUserId(userId);
        if (userExists.isEmpty()) {
            throw new NotFoundException("Utente non trovato");
        }

        // Verifica che il ruolo dell'utente sia ECONOMO.
        if (!userExists.get().getRuolo().equals(Ruolo.ECONOMO)) {
            throw new BadRequestException("Ruolo non valido");
        }

        // Prende dal database tutti i tabacchi gestiti dall'economo.
        List<Tabacchi> tabacchi = tabacchiRepository.findAllByEconomo(userExists.get());

        // Inizializza la variabile per memorizzare tutte le ricariche.
        List<Ricarica> ricariche = new ArrayList<>();
        for (Tabacchi t : tabacchi) {
            ricariche.addAll(ricaricaRepository.findAllByTabacchi(t));
        }

        // Inizializza la variabile di risposta.
        List<GetAllRichiesteRicaricaSaldoResponse> response = new ArrayList<>();
        for (Ricarica r : ricariche) {
            // Aggiunge alla risposta solo le ricariche che sono richieste e non ancora accettate.
            if (!r.isAccettata() && r.isRichiesta()) {
                response.add(new GetAllRichiesteRicaricaSaldoResponse(
                        r.getRicaricaId(),
                        r.getPlayer().getUserId(),
                        r.getPlayer().getNome(),
                        r.getPlayer().getCognome(),
                        r.getPlayer().getEmail(),
                        r.getPlayer().getUsername(),
                        r.getDataRichiesta(),
                        r.getImporto()
                ));
            }
        }

        return response;
    }

    /**
     * Accetta una richiesta di ricarica e aggiorna il saldo del giocatore.
     *
     * @param ricaricaId ID della richiesta di ricarica da accettare.
     * @param playerId ID del giocatore a cui accreditare la ricarica.
     * @throws BadRequestException se uno degli ID forniti non è valido.
     * @throws NotFoundException se la ricarica o il giocatore non vengono trovati.
     */
    @Override
    public void accettaRicarica(Long ricaricaId, Long playerId) {
        // Verifica che l'ID della ricarica sia valido.
        if (ricaricaId < 1) {
            throw new BadRequestException("Id non Valido");
        }

        // Controlla se la ricarica esiste nel database.
        Optional<Ricarica> ricaricaExists = ricaricaRepository.findById(ricaricaId);
        if (ricaricaExists.isEmpty()) {
            throw new NotFoundException("Ricarica non trovata");
        }

        Ricarica ricarica = ricaricaExists.get();

        // Imposta la ricarica come accettata e aggiorna il database.
        ricarica.setRichiesta(false);
        ricarica.setAccettata(true);
        ricaricaRepository.save(ricarica);

        // Verifica che l'ID del giocatore sia valido.
        if (playerId < 1) {
            throw new BadRequestException("Id player non Valido");
        }

        // Controlla se il giocatore esiste nel database.
        Optional<User> playerExists = userRepository.findById(playerId);
        if (playerExists.isEmpty()) {
            throw new NotFoundException("Player non trovato");
        }

        // Aggiorna il saldo del giocatore e salva nel database.
        playerExists.get().setSaldo(playerExists.get().getSaldo() + ricarica.getImporto());
        userRepository.save(playerExists.get());

        // Crea e salva una notifica per informare il giocatore dell'accettazione della ricarica.
        notificaRepository.save(new NotificaBuilder()
                .data(LocalDateTime.now())
                .testo("Ricarica di " + ricarica.getImporto() + "€ accettata")
                .player(playerExists.get())
                .build());
    }

    /**
     * Rifiuta una richiesta di ricarica e notifica il giocatore.
     *
     * @param ricaricaId ID della richiesta di ricarica da rifiutare.
     * @throws BadRequestException se l'ID della ricarica non è valido.
     * @throws NotFoundException se la ricarica non viene trovata.
     */
    @Override
    public void rifiutaRicarica(Long ricaricaId) {
        // Verifica che l'ID della ricarica sia valido.
        if (ricaricaId < 1) {
            throw new BadRequestException("Id non Valido");
        }

        // Controlla se la ricarica esiste nel database.
        Optional<Ricarica> ricaricaExists = ricaricaRepository.findById(ricaricaId);
        if (ricaricaExists.isEmpty()) {
            throw new NotFoundException("Ricarica non trovata");
        }

        Ricarica ricarica = ricaricaExists.get();

        // Elimina la ricarica dal database.
        ricaricaRepository.delete(ricarica);

        // Crea e salva una notifica per informare il giocatore del rifiuto della ricarica.
        notificaRepository.save(new NotificaBuilder()
                .data(LocalDateTime.now())
                .testo("Ricarica di " + ricarica.getImporto() + "€ rifiutata")
                .player(ricarica.getPlayer())
                .build());
    }
}
