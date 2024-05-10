package it.polimi.blackjackbe.dto.response;

import it.polimi.blackjackbe.model.TipoTavolo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
//TODO: Capire dove includere il ruolo del mittente del messaggio
public class GetAllMessagesByTipoTavoloResponse {
    private String testoMessaggio;
    private LocalDateTime createdAt;
    private String usernameMittente;
}
