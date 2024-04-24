package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.request.CreaTabacchiRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.MessageResponse;
import it.polimi.blackjackbe.dto.response.TabacchiResponse;
import it.polimi.blackjackbe.dto.response.UserResponse;
import it.polimi.blackjackbe.service.definition.TabacchiService;
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
@RequestMapping("/api/v1/tabacchi")
public class TabacchiController {
    private final TabacchiService tabacchiService;

    @PostMapping("/creaTabacchi")
    public ResponseEntity<MessageResponse> creaTabacchi(@Valid @RequestBody CreaTabacchiRequest request) {
        tabacchiService.creaTabacchi(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse("Tabacchi creato con successo"));
    }

    @GetMapping("getAllTabacchi")
    public ResponseEntity<List<TabacchiResponse>> getAllTabacchi() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tabacchiService.getAllTabacchi());
    }

    @DeleteMapping("eliminaTabacchi/{tabacchiId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteTabacchi(@PathVariable String tabacchiId) {
        tabacchiService.deleteTabacchi(Long.parseLong(tabacchiId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Tabacchi eliminato con successo"));
    }
}
