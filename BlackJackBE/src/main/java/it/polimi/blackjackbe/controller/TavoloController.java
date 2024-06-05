package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.command.CommandExecutor;
import it.polimi.blackjackbe.dto.request.EndTavoloRequest;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.service.definition.TavoloService;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

/**
 * Controller per la gestione dei tavoli nel gioco del Blackjack.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tavolo")
public class TavoloController {

    private final TavoloService tavoloService;
    private final CommandExecutor commandExecutor;

    /**
     * Endpoint per inizializzare un nuovo tavolo.
     * @param tipoTavolo Tipo di tavolo da inizializzare.
     * @param userId ID dell'utente che inizializza il tavolo.
     * @return Messaggio di conferma dell'inizializzazione del tavolo con lo stato HTTP 200 OK.
     */
    @GetMapping("/init/{tipoTavolo}/{userId}")
    public ResponseEntity<MessageResponse> init(@PathVariable String tipoTavolo, @PathVariable Long userId) {
        // Chiama il servizio per inizializzare il tavolo con il tipo e l'utente specificati
        tavoloService.init(tipoTavolo, userId);

        // Restituisce una risposta di successo con lo stato HTTP 200 OK
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Tavolo inizializzato"));
    }

    /**
     * Endpoint per terminare un tavolo.
     * @param userId ID dell'utente che termina il tavolo.
     * @param request DTO con i dati per terminare il tavolo.
     * @return Messaggio di conferma della terminazione del tavolo con lo stato HTTP 200 OK.
     */
    @PostMapping("/end/{userId}")
    public ResponseEntity<MessageResponse> end(@PathVariable Long userId, @RequestBody EndTavoloRequest request) {
        // Chiama il servizio per terminare il tavolo per l'utente specificato
        tavoloService.end(userId, request);

        // Restituisce una risposta di successo con lo stato HTTP 200 OK
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Tavolo terminato"));
    }

    /**
     * Endpoint per eseguire un comando su un tavolo.
     * @param command Nome del comando da eseguire.
     * @param userId ID dell'utente che esegue il comando.
     * @param data Dati aggiuntivi richiesti per l'esecuzione del comando.
     * @return Lo stato del tavolo dopo l'esecuzione del comando.
     */
    @PostMapping("/{command}/{userId}")
    public ResponseEntity<TavoloStatusResponse> command(@PathVariable String command, @PathVariable Long userId, @RequestBody Map<String, Object> data) {
        // Stampa i dati ricevuti per il debug
        System.out.println("Received data: " + data);

        // Esegue il comando specificato e restituisce la risposta con lo stato del tavolo
        return ResponseEntity
                .ok(commandExecutor.executeCommand(command, userId, data));
    }

    /**
     * Endpoint per ottenere i nomi di tutti i comandi disponibili.
     * @return Una collezione di nomi di comandi disponibili.
     */
    @GetMapping("/getCommandsAvaliable")
    public ResponseEntity<Collection<String>> getCommands() {
        // Restituisce i nomi di tutti i comandi disponibili
        return ResponseEntity
                .ok(commandExecutor.getCommandNames());
    }
}
