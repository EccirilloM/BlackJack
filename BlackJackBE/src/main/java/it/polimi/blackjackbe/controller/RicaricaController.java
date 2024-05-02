package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.request.RichiestaRicaricaRequest;
import it.polimi.blackjackbe.dto.response.AccettaRichiestaRequest;
import it.polimi.blackjackbe.dto.response.GetAllRichiesteRicaricaSaldoResponse;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.dto.response.UserResponse;
import it.polimi.blackjackbe.service.definition.RicaricaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ricarica")
public class RicaricaController {

    private final RicaricaService ricaricaService;
    @PostMapping("/richiediRicarica/{userId}")
    public ResponseEntity<MessageResponse> richiediRicarica(@PathVariable String userId, @RequestBody RichiestaRicaricaRequest request) {
        ricaricaService.richiediRicarica(Long.parseLong(userId), request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Richiesta effettuata con successo"));
    }

    @GetMapping("/getAllRichiesteByEconomo/{userId}")
    public ResponseEntity<List<GetAllRichiesteRicaricaSaldoResponse>> getAllRichiesteRicarica(@PathVariable String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ricaricaService.getAllRichiesteRicarica(Long.parseLong(userId)));
    }

    @PutMapping("/accettaRichiesta")
    public ResponseEntity<MessageResponse> accettaRichiesta(@RequestBody AccettaRichiestaRequest request) {
        ricaricaService.accettaRicarica(request.getRichiestaId(), request.getPlayerId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Richiesta accettata con successo"));
    }

    @DeleteMapping("/rifiutaRichiesta/{richiestaId}")
    public ResponseEntity<MessageResponse> rifiutaRichiesta(@PathVariable String richiestaId) {
        ricaricaService.rifiutaRicarica(Long.parseLong(richiestaId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Richiesta rifiutata con successo"));
    }
}
