package it.polimi.blackjackbe.controller;
import it.polimi.blackjackbe.dto.response.NotificaResponse;
import it.polimi.blackjackbe.service.definition.NotificaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller per la gestione delle notifiche delle ricariche del saldo.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifica")
public class NotificaController {

    private final NotificaService notificaService;

    /**
     * Endpoint per ottenere tutte le notifiche di un utente specifico.
     * @param userId ID dell'utente di cui si vogliono ottenere le notifiche.
     * @return Una lista delle notifiche dell'utente specificato con lo stato HTTP 200 OK.
     */
    @GetMapping("/getAllNotificheByUserId/{userId}")
    public ResponseEntity<List<NotificaResponse>> getAllByUserId(@PathVariable String userId) {
        // Chiama il servizio per ottenere tutte le notifiche dell'utente specificato e restituisce la risposta
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificaService.getAllByUserId(Long.parseLong(userId)));
    }
}
