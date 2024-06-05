package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.request.CreaTabacchiRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.dto.response.TabacchiResponse;
import it.polimi.blackjackbe.dto.response.UserResponse;
import it.polimi.blackjackbe.service.definition.TabacchiService;
import it.polimi.blackjackbe.service.definition.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per la gestione dei tabacchi (ATM) per la ricarica del saldo degli utenti.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tabacchi")
public class TabacchiController {
    private final TabacchiService tabacchiService;

    /**
     * Endpoint per creare un nuovo tabacchi.
     * @param request DTO con i dati per la creazione del tabacchi.
     * @return Messaggio di conferma della creazione del tabacchi con lo stato HTTP 201 Created.
     */
    @PostMapping("/creaTabacchi")
    public ResponseEntity<MessageResponse> creaTabacchi(@Valid @RequestBody CreaTabacchiRequest request) {
        // Chiama il servizio per creare un nuovo tabacchi
        tabacchiService.creaTabacchi(request);

        // Restituisce una risposta di successo con lo stato HTTP 201 Created
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse("Tabacchi creato con successo"));
    }

    /**
     * Endpoint per ottenere tutti i tabacchi.
     * @return Una lista di tutti i tabacchi con lo stato HTTP 200 OK.
     */
    @GetMapping("getAllTabacchi")
    public ResponseEntity<List<TabacchiResponse>> getAllTabacchi() {
        // Chiama il servizio per ottenere tutti i tabacchi e restituisce la risposta
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tabacchiService.getAllTabacchi());
    }

    /**
     * Endpoint per eliminare un tabacchi.
     * Richiede il ruolo di ADMIN.
     * @param tabacchiId ID del tabacchi da eliminare.
     * @return Messaggio di conferma dell'eliminazione del tabacchi con lo stato HTTP 200 OK.
     */
    @DeleteMapping("eliminaTabacchi/{tabacchiId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteTabacchi(@PathVariable String tabacchiId) {
        // Chiama il servizio per eliminare il tabacchi specificato
        tabacchiService.deleteTabacchi(Long.parseLong(tabacchiId));

        // Restituisce una risposta di successo con lo stato HTTP 200 OK
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Tabacchi eliminato con successo"));
    }
}
