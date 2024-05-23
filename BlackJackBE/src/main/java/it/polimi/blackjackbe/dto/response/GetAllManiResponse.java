package it.polimi.blackjackbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetAllManiResponse {
    private Long manoId;
    private Long tavoloId;
    private String playerUsername;
    private LocalDateTime dataMano;
    private Double importo;
}
