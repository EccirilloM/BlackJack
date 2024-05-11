package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Carta;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TavoloBuilder {
    private Long tavoloId;
    private TipoTavolo tipoTavolo;
    private boolean vittoriaUser;
    private Double plotUser; //Quanto punta l'utente
    private User player;
    private List<Carta> carte;
    private List<Carta> carteSingolaMano;
    private Carta cartaDealer;

    public TavoloBuilder tavoloId(Long tavoloId) {
        this.tavoloId = tavoloId;
        return this;
    }

    public TavoloBuilder tipoTavolo(TipoTavolo tipoTavolo) {
        this.tipoTavolo = tipoTavolo;
        return this;
    }

    public TavoloBuilder vittoriaUser(boolean vittoriaUser) {
        this.vittoriaUser = vittoriaUser;
        return this;
    }

    public TavoloBuilder plotUser(Double plotUser) {
        this.plotUser = plotUser;
        return this;
    }

    public TavoloBuilder player(User player) {
        this.player = player;
        return this;
    }

    public TavoloBuilder carte(List<Carta> carte) {
        this.carte = carte;
        return this;
    }

    public TavoloBuilder carteSingolaMano(List<Carta> carteSingolaMano) {
        this.carteSingolaMano = carteSingolaMano;
        return this;
    }

    public TavoloBuilder cartaDealer(Carta cartaDealer) {
        this.cartaDealer = cartaDealer;
        return this;
    }

    public Tavolo build() {
        return new Tavolo(this);
    }
}
