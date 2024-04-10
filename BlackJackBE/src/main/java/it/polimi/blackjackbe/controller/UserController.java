package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.request.AggiornaDatiRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.dto.response.UserResponse;
import it.polimi.blackjackbe.service.definition.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                        .body(userService.getUserData(Long.parseLong(userId)));
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<MessageResponse> deleteAccount(@PathVariable String userId) {
        userService.deleteUser(Long.parseLong(userId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Account eliminato con successo"));
    }

    @GetMapping("getAll/{ruolo}")
    public ResponseEntity<List<UserResponse>> getAllByRuolo(@PathVariable String ruolo) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllByRuolo(ruolo));
    }

    @GetMapping("getAll")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAll());
    }

    @PutMapping("aggiornaDatiUtente/{userId}")
    public ResponseEntity<UserResponse> aggiornaDatiUtente(@RequestBody AggiornaDatiRequest aggiornaRequest, @PathVariable String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.aggiornaDatiUtente(aggiornaRequest, Long.parseLong(userId)));
    }
}
