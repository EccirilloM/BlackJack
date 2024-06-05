package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.response.GetAllManiResponse;
import it.polimi.blackjackbe.dto.response.TabacchiResponse;
import it.polimi.blackjackbe.service.definition.ManoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller per la gestione delle mani nel gioco del Blackjack.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mano")
public class ManoController {
    private final ManoService manoService;

    /**
     * Endpoint per ottenere tutte le mani.
     * @return Una lista di tutte le mani con lo stato HTTP 200 OK.
     */
    @GetMapping("/getAllMani")
    public ResponseEntity<List<GetAllManiResponse>> getAllMani() {
        // Chiama il servizio per ottenere tutte le mani e restituisce la risposta
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(manoService.getAllMani());
    }

    /**
     * Endpoint per ottenere tutte le mani di un utente specifico.
     * @param userId ID dell'utente di cui si vogliono ottenere le mani.
     * @return Una lista delle mani dell'utente specificato con lo stato HTTP 200 OK.
     */
    @GetMapping("/getAllManiByUserId/{userId}")
    public ResponseEntity<List<GetAllManiResponse>> getAllMani(@PathVariable String userId) {
        // Chiama il servizio per ottenere tutte le mani dell'utente specificato e restituisce la risposta
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(manoService.getAllManiByUserId(Long.parseLong(userId)));
    }
}
