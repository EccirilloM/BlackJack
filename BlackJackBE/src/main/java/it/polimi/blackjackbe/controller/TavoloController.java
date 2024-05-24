package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.command.CommandExecutor;
import it.polimi.blackjackbe.dto.request.EndTavoloRequest;
import it.polimi.blackjackbe.dto.response.CartaResponse;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.service.definition.TavoloService;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tavolo")
public class TavoloController {

    private final TavoloService tavoloService;
    private final CommandExecutor commandExecutor;

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


    @PostMapping("/{command}/{userId}")
    public ResponseEntity<TavoloStatusResponse> command(@PathVariable String command, @PathVariable Long userId, @RequestBody Map<String, Object> data){
        System.out.println("Received data: " + data);
        return ResponseEntity
                .ok(commandExecutor.executeCommand(command, userId, data));
    }

    @GetMapping("/getCommandsAvaliable")
    public ResponseEntity<Collection<String>> getCommands() {
        return ResponseEntity
                .ok(commandExecutor.getCommandNames());
    }

}
