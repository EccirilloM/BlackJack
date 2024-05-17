package it.polimi.blackjackbe.dto.response;

import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.TipoTavolo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetAllMessagesByTipoTavoloResponse {
    private String testoMessaggio;
    private LocalDateTime createdAt;
    private String usernameMittente;
    private String ruoloMittente;
}
