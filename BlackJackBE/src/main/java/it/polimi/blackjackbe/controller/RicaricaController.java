package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.request.RichiestaRicaricaRequest;
import it.polimi.blackjackbe.dto.response.AccettaRichiestaRequest;
import it.polimi.blackjackbe.dto.response.GetAllRichiesteRicaricaSaldoResponse;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.dto.response.UserResponse;
import it.polimi.blackjackbe.service.definition.RicaricaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per la gestione delle ricariche del saldo degli utenti.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ricarica")
public class RicaricaController {

    private final RicaricaService ricaricaService;

    /**
     * Endpoint per richiedere una ricarica.
     * @param userId ID dell'utente che richiede la ricarica.
     * @param request DTO con i dati della richiesta di ricarica.
     * @return Messaggio di conferma della richiesta di ricarica con lo stato HTTP 200 OK.
     */
    @PostMapping("/richiediRicarica/{userId}")
    public ResponseEntity<MessageResponse> richiediRicarica(@PathVariable String userId, @RequestBody RichiestaRicaricaRequest request) {
        // Chiama il servizio per richiedere una ricarica per l'utente specificato
        ricaricaService.richiediRicarica(Long.parseLong(userId), request);

        // Restituisce una risposta di successo con lo stato HTTP 200 OK
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Richiesta effettuata con successo"));
    }

    /**
     * Endpoint per ottenere tutte le richieste di ricarica per un economo specifico.
     * @param userId ID dell'economo di cui si vogliono ottenere le richieste di ricarica.
     * @return Una lista delle richieste di ricarica dell'economo specificato con lo stato HTTP 200 OK.
     */
    @GetMapping("/getAllRichiesteByEconomo/{userId}")
    public ResponseEntity<List<GetAllRichiesteRicaricaSaldoResponse>> getAllRichiesteRicarica(@PathVariable String userId) {
        // Chiama il servizio per ottenere tutte le richieste di ricarica per l'economo specificato
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ricaricaService.getAllRichiesteRicarica(Long.parseLong(userId)));
    }

    /**
     * Endpoint per accettare una richiesta di ricarica.
     * @param request DTO con i dati della richiesta da accettare.
     * @return Messaggio di conferma dell'accettazione con lo stato HTTP 200 OK.
     */
    @PutMapping("/accettaRichiesta")
    public ResponseEntity<MessageResponse> accettaRichiesta(@RequestBody AccettaRichiestaRequest request) {
        // Chiama il servizio per accettare la richiesta di ricarica
        ricaricaService.accettaRicarica(request.getRichiestaId(), request.getPlayerId());

        // Restituisce una risposta di successo con lo stato HTTP 200 OK
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Richiesta accettata con successo"));
    }

    /**
     * Endpoint per rifiutare una richiesta di ricarica.
     * @param richiestaId ID della richiesta di ricarica da rifiutare.
     * @return Messaggio di conferma del rifiuto con lo stato HTTP 200 OK.
     */
    @DeleteMapping("/rifiutaRichiesta/{richiestaId}")
    public ResponseEntity<MessageResponse> rifiutaRichiesta(@PathVariable String richiestaId) {
        // Chiama il servizio per rifiutare la richiesta di ricarica
        ricaricaService.rifiutaRicarica(Long.parseLong(richiestaId));

        // Restituisce una risposta di successo con lo stato HTTP 200 OK
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Richiesta rifiutata con successo"));
    }
}
