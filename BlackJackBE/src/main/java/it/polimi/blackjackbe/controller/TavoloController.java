package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.request.EndTavoloRequest;
import it.polimi.blackjackbe.dto.response.CartaResponse;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.dto.response.TabacchiResponse;
import it.polimi.blackjackbe.model.Carta;
import it.polimi.blackjackbe.service.definition.TavoloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tavolo")
public class TavoloController {

    private final TavoloService tavoloService;

    @GetMapping("/chiediCarta/{userId}")
    public ResponseEntity<CartaResponse> chiediCarta(@PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tavoloService.chiediCarta(userId));
    }

    @GetMapping("/init/{tipoTavolo}/{userId}")
    public ResponseEntity<MessageResponse> init(@PathVariable String tipoTavolo, @PathVariable Long userId) {

        tavoloService.init(tipoTavolo, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Tavolo inizializzato"));
    }

    @PostMapping("/end/{userId}")
    public ResponseEntity<MessageResponse> end(@PathVariable Long userId, @RequestBody EndTavoloRequest request) {
        tavoloService.end(userId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Tavolo terminato"));
    }

}
