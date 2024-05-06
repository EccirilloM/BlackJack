package it.polimi.blackjackbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NotificaResponse {
    private LocalDateTime data;
    private String messaggio;
}
