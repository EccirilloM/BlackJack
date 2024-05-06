package it.polimi.blackjackbe.controller;


import it.polimi.blackjackbe.dto.response.NotificaResponse;
import it.polimi.blackjackbe.service.definition.NotificaService;
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
@RequestMapping("/api/v1/notifica")
public class NotificaController {

    private final NotificaService notificaService;

    @GetMapping("/getAllNotificheByUserId/{userId}")
    public ResponseEntity<List<NotificaResponse>> getAllByUserId(@PathVariable String userId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificaService.getAllByUserId(Long.parseLong(userId)));
    }
}
