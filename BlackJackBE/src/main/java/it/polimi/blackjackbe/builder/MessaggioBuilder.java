package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Messaggio;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MessaggioBuilder {
    private Long messaggioId;
    private String testo;
    private LocalDateTime createdAt;
    private TipoTavolo tipoTavolo;
    private User user; // user che ha inviato il messaggio

    public MessaggioBuilder messaggioId(Long messaggioId) {
        this.messaggioId = messaggioId;
        return this;
    }

    public MessaggioBuilder testo(String testo) {
        this.testo = testo;
        return this;
    }

    public MessaggioBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MessaggioBuilder tipoTavolo(TipoTavolo tipoTavolo) {
        this.tipoTavolo = tipoTavolo;
        return this;
    }

    public MessaggioBuilder user(User user) {
        this.user = user;
        return this;
    }

    public Messaggio build() {
        return new Messaggio(this);
    }
}
