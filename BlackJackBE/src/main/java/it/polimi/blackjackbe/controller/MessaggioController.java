package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.request.MessaggioRequest;
import it.polimi.blackjackbe.dto.response.GetAllMessagesByTipoTavoloResponse;
import it.polimi.blackjackbe.dto.response.GetAllRichiesteRicaricaSaldoResponse;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.service.definition.MessaggioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messaggio")
public class MessaggioController {

    private final MessaggioService messaggioService;

    @GetMapping("/getAllMessageByTipoTavolo/{tipoTavolo}")
    public ResponseEntity<List<GetAllMessagesByTipoTavoloResponse>> getAllMessageByTipoTavolo(@PathVariable String tipoTavolo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messaggioService.getAllMessagesByTipoTavolo(tipoTavolo));
    }

    @PostMapping("/invia")
    public ResponseEntity<MessageResponse> invia(@RequestBody MessaggioRequest request) {
        messaggioService.inviaMessaggio(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Messaggio inviato con successo"));
    }
}
