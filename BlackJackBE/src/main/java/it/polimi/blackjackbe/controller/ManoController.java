package it.polimi.blackjackbe.controller;

import it.polimi.blackjackbe.dto.response.GetAllManiResponse;
import it.polimi.blackjackbe.dto.response.TabacchiResponse;
import it.polimi.blackjackbe.service.definition.ManoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mano")
public class ManoController {
    private final ManoService manoService;

    @GetMapping("/getAllMani")
    public ResponseEntity<List<GetAllManiResponse>> getAllMani() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(manoService.getAllMani());
    }

    @GetMapping("/getAllManiByUserId/{userId}")
    public ResponseEntity<List<GetAllManiResponse>> getAllMani(@PathVariable String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(manoService.getAllManiByUserId(Long.parseLong(userId)));
    }


}
