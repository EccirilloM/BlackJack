package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.request.AdminAggiornaDatiUtenteRequest;
import it.polimi.blackjackbe.dto.request.AggiornaDatiRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.dto.response.UserResponse;
import it.polimi.blackjackbe.service.definition.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per la gestione degli utenti nel sistema.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    /**
     * Endpoint per ottenere i dati di un utente specifico.
     * @param userId ID dell'utente di cui si vogliono ottenere i dati.
     * @return I dati dell'utente specificato con lo stato HTTP 200 OK.
     */
    @GetMapping("/getUserDataById/{userId}")
    public ResponseEntity<UserResponse> getUserDataById(@PathVariable String userId) {
        // Chiama il servizio per ottenere i dati dell'utente specificato e restituisce la risposta
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserDataById(Long.parseLong(userId)));
    }

    /**
     * Endpoint per eliminare un account utente.
     * @param userId ID dell'utente da eliminare.
     * @return Messaggio di conferma dell'eliminazione dell'account con lo stato HTTP 200 OK.
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<MessageResponse> deleteAccount(@PathVariable String userId) {
        // Chiama il servizio per eliminare l'account dell'utente specificato
        userService.deleteUser(Long.parseLong(userId));

        // Restituisce una risposta di successo con lo stato HTTP 200 OK
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Account eliminato con successo"));
    }

    /**
     * Endpoint per ottenere tutti gli utenti.
     * @return Una lista di tutti gli utenti con lo stato HTTP 200 OK.
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAll() {
        // Chiama il servizio per ottenere tutti gli utenti e restituisce la risposta
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAll());
    }

    /**
     * Endpoint per ottenere tutti gli utenti con un ruolo specifico.
     * @param ruolo Il ruolo degli utenti da ottenere.
     * @return Una lista degli utenti con il ruolo specificato con lo stato HTTP 200 OK.
     */
    @GetMapping("/getAllByRuolo/{ruolo}")
    public ResponseEntity<List<UserResponse>> getAllByRuolo(@PathVariable String ruolo) {
        // Chiama il servizio per ottenere tutti gli utenti con il ruolo specificato e restituisce la risposta
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllByRuolo(ruolo));
    }

    /**
     * Endpoint per aggiornare i dati di un utente.
     * @param aggiornaRequest DTO con i nuovi dati dell'utente.
     * @param userId ID dell'utente da aggiornare.
     * @return I dati aggiornati dell'utente con lo stato HTTP 200 OK.
     */
    @PutMapping("/aggiornaDatiUtente/{userId}")
    public ResponseEntity<UserResponse> aggiornaDatiUtente(@RequestBody AggiornaDatiRequest aggiornaRequest, @PathVariable String userId) {
        // Chiama il servizio per aggiornare i dati dell'utente specificato e restituisce la risposta
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.aggiornaDatiUtente(aggiornaRequest, Long.parseLong(userId)));
    }

    /**
     * Endpoint per un amministratore per aggiornare i dati di un utente.
     * @param request DTO con i nuovi dati dell'utente.
     * @param userId ID dell'utente da aggiornare.
     * @return I dati aggiornati dell'utente con lo stato HTTP 200 OK.
     */
    @PutMapping("/adminAggiornaDatiUtente/{userId}")
    public ResponseEntity<UserResponse> adminAggiornaDatiUtente(@RequestBody AdminAggiornaDatiUtenteRequest request, @PathVariable String userId) {
        // Chiama il servizio per un amministratore per aggiornare i dati dell'utente specificato e restituisce la risposta
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.adminAggiornaDatiUtente(request, Long.parseLong(userId)));
    }

    /**
     * Endpoint per creare un nuovo economo.
     * @param request DTO con i dati per la registrazione dell'economo.
     * @return Messaggio di conferma della registrazione con lo stato HTTP 201 Created.
     */
    @PostMapping("/creaEconomo")
    public ResponseEntity<MessageResponse> creaEconomo(@Valid @RequestBody RegistrazioneRequest request) {
        // Chiama il servizio per creare un nuovo economo
        userService.creaEconomo(request);

        // Restituisce una risposta di successo con lo stato HTTP 201 Created
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse("Registrazione effettuata con successo"));
    }
}
