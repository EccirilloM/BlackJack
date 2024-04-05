package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.request.LoginRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.LoginResponse;
import it.polimi.blackjackbe.model.Utente;
import it.polimi.blackjackbe.service.definition.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UtenteService utenteService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Utente u = null;
        try {
            u = utenteService.login(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (u == null) {
            return ResponseEntity.notFound().build();
        }
        LoginResponse l = new LoginResponse();
        l.setId(u.getId());
        l.setUsername(u.getUsername());
        l.setRuolo(u.getRuolo().name());
        return ResponseEntity.status(HttpStatus.OK).body(l);
    }

    @PostMapping("/registrazionePlayer")
    public ResponseEntity<Void> registrazionePlayer(@RequestBody RegistrazioneRequest registrazioneRequest) {
        boolean registrato = utenteService.registrazionePlayer(registrazioneRequest);
        if (registrato) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
