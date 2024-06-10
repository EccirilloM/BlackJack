package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.builder.MessaggioBuilder;
import it.polimi.blackjackbe.dto.request.MessaggioRequest;
import it.polimi.blackjackbe.dto.response.GetAllMessagesByTipoTavoloResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.Messaggio;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.MessaggioRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.MessaggioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del service che gestisce le operazioni relative ai messaggi nel'applicazione.
 */
@Service
@RequiredArgsConstructor
public class MessaggioServiceImplementation implements MessaggioService {

    private final UserRepository userRepository;
    private final MessaggioRepository messaggioRepository;

    /**
     * Recupera tutti i messaggi associati a un determinato tipo di tavolo.
     * @param tipoTavoloRequest Il tipo di tavolo per cui recuperare i messaggi.
     * @return Lista di risposte contenenti tutti i messaggi del tipo di tavolo specificato.
     */
    @Override
    public List<GetAllMessagesByTipoTavoloResponse> getAllMessagesByTipoTavolo(String tipoTavoloRequest) {
        // Determina il tipo di tavolo dall'input.
        TipoTavolo tipoTavolo = switch (tipoTavoloRequest) {
            case "BASE" -> TipoTavolo.BASE;
            case "PREMIUM" -> TipoTavolo.PREMIUM;
            case "VIP" -> TipoTavolo.VIP;
            case "EXCLUSIVE" -> TipoTavolo.EXCLUSIVE;
            default -> throw new BadRequestException("Tipo Tavolo non valido");
        };

        // Prende dal database tutti i messaggi per il tipo di tavolo specificato.
        List<Messaggio> messaggi = messaggioRepository.findAllByTipoTavolo(tipoTavolo);

        // Inizializza la variabile di risposta.
        List<GetAllMessagesByTipoTavoloResponse> response = new ArrayList<>();

        // Per ogni messaggio, crea un oggetto di risposta e lo aggiunge alla lista di risposta.
        for (Messaggio messaggio : messaggi) {
            response.add(new GetAllMessagesByTipoTavoloResponse(
                    messaggio.getTesto(),
                    messaggio.getCreatedAt(),
                    messaggio.getUser().getUsername(),
                    messaggio.getUser().getRuolo().toString())
            );
        }

        return response;
    }

    /**
     * Invia un nuovo messaggio.
     * @param messaggioRequest DTO contenente i dati del messaggio da inviare.
     */
    @Override
    public void inviaMessaggio(MessaggioRequest messaggioRequest) {
        // Verifica che il testo del messaggio non sia vuoto o nullo.
        if (messaggioRequest.getTesto().isEmpty() || messaggioRequest.getTesto().isBlank()) {
            throw new IllegalArgumentException("Testo Messaggio non valido");
        }

        // Verifica che l'ID del mittente non sia nullo.
        if (messaggioRequest.getMittenteId() == null) {
            throw new IllegalArgumentException("Id Utente non valido");
        }

        // Determina il tipo di tavolo dall'input.
        TipoTavolo tipoTavolo = switch (messaggioRequest.getTipoTavolo()) {
            case "BASE" -> TipoTavolo.BASE;
            case "PREMIUM" -> TipoTavolo.PREMIUM;
            case "VIP" -> TipoTavolo.VIP;
            case "EXCLUSIVE" -> TipoTavolo.EXCLUSIVE;
            default -> throw new BadRequestException("Tipo Tavolo non valido");
        };

        // Verifica che l'utente esista nel database.
        Optional<User> userExists = userRepository.findByUserId(messaggioRequest.getMittenteId());
        if (userExists.isEmpty()) {
            throw new NotFoundException("Utente non trovato");
        }

        // Costruisce l'oggetto messaggio con il pattern builder.
        Messaggio messaggio = new MessaggioBuilder()
                .testo(messaggioRequest.getTesto())
                .user(userExists.get())
                .createdAt(LocalDateTime.now())
                .tipoTavolo(tipoTavolo)
                .build();

        // Salva il messaggio nel database.
        messaggioRepository.save(messaggio);
    }
}
