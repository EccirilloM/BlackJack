package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Ricarica;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TabacchiBuilder {
    private Long tabacchiId;
    private String nome;
    private Double lat;
    private Double lng;
    private List<Ricarica> ricariche; //Ricariche effettuate nel singolo tabacchi
    private User economo; //Economo che gestisce il tabacchi

    public TabacchiBuilder tabacchiId(Long tabacchiId) {
        this.tabacchiId = tabacchiId;
        return this;
    }

    public TabacchiBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public TabacchiBuilder lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public TabacchiBuilder lng(Double lng) {
        this.lng = lng;
        return this;
    }

    public TabacchiBuilder ricariche(List<Ricarica> ricariche) {
        this.ricariche = ricariche;
        return this;
    }

    public TabacchiBuilder economo(User economo) {
        this.economo = economo;
        return this;
    }

    public Tabacchi build() {
        return new Tabacchi(this);
    }
}
