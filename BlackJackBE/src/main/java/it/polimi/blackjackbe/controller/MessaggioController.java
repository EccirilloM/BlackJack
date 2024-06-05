package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.request.MessaggioRequest;
import it.polimi.blackjackbe.dto.response.GetAllMessagesByTipoTavoloResponse;
import it.polimi.blackjackbe.dto.response.GetAllRichiesteRicaricaSaldoResponse;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.service.definition.MessaggioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per la gestione dei messaggi del Forum.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messaggio")
public class MessaggioController {

    private final MessaggioService messaggioService;

    /**
     * Endpoint per ottenere tutti i messaggi di un tipo specifico di tavolo.
     * @param tipoTavolo Tipo di tavolo di cui si vogliono ottenere i messaggi.
     * @return Una lista dei messaggi del tipo di tavolo specificato con lo stato HTTP 200 OK.
     */
    @GetMapping("/getAllMessageByTipoTavolo/{tipoTavolo}")
    public ResponseEntity<List<GetAllMessagesByTipoTavoloResponse>> getAllMessageByTipoTavolo(@PathVariable String tipoTavolo) {
        // Chiama il servizio per ottenere tutti i messaggi del tipo di tavolo specificato e restituisce la risposta
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messaggioService.getAllMessagesByTipoTavolo(tipoTavolo));
    }

    /**
     * Endpoint per inviare un nuovo messaggio.
     * @param request DTO con i dati del messaggio da inviare.
     * @return Messaggio di conferma dell'invio con lo stato HTTP 200 OK.
     */
    @PostMapping("/invia")
    public ResponseEntity<MessageResponse> invia(@RequestBody MessaggioRequest request) {
        // Chiama il servizio per inviare il messaggio
        messaggioService.inviaMessaggio(request);

        // Restituisce una risposta di successo con lo stato HTTP 200 OK
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Messaggio inviato con successo"));
    }
}
