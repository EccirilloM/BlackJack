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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    @GetMapping("/getUserData/{userId}")
    public ResponseEntity<UserResponse> getUserDataById(@PathVariable String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                        .body(userService.getUserDataById(Long.parseLong(userId)));
    }

    @DeleteMapping("delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteAccount(@PathVariable String userId) {
        userService.deleteUser(Long.parseLong(userId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Account eliminato con successo"));
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAll());
    }

    @GetMapping("getAllByRuolo/{ruolo}")
    public ResponseEntity<List<UserResponse>> getAllByRuolo(@PathVariable String ruolo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllByRuolo(ruolo));
    }

    @PutMapping("aggiornaDatiUtente/{userId}")
    public ResponseEntity<UserResponse> aggiornaDatiUtente(@RequestBody AggiornaDatiRequest aggiornaRequest, @PathVariable String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.aggiornaDatiUtente(aggiornaRequest, Long.parseLong(userId)));
    }

    @PutMapping("/adminAggiornaDatiUtente/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> adminAggiornaDatiUtente(@RequestBody AdminAggiornaDatiUtenteRequest request, @PathVariable String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.adminAggiornaDatiUtente(request, Long.parseLong(userId)));
    }

    @PostMapping("/creaEconomo")
    public ResponseEntity<MessageResponse> creaEconomo(@Valid @RequestBody RegistrazioneRequest request) {
        userService.creaEconomo(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse("Registrazione effettuata con successo"));
    }
}
