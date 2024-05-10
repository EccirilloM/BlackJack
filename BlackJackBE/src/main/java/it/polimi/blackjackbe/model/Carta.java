package it.polimi.blackjackbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Carta {
    private String seme;
    private String valore;
    private int punteggio;



}
