package it.polimi.blackjackbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetAllRichiesteRicaricaSaldoResponse {
    private Long richiestaId;
    private Long playerId;
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private LocalDateTime dataRichiesta;
    private Double importo;
}
