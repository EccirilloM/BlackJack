package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Notifica;
import it.polimi.blackjackbe.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NotificaBuilder {
    private Long notificaId;
    private LocalDateTime data;
    private String testo;
    private User player;

    public NotificaBuilder notificaId(Long notificaId) {
        this.notificaId = notificaId;
        return this;
    }

    public NotificaBuilder data(LocalDateTime data) {
        this.data = data;
        return this;
    }

    public NotificaBuilder testo(String testo) {
        this.testo = testo;
        return this;
    }

    public NotificaBuilder player(User player) {
        this.player = player;
        return this;
    }

    public Notifica build() {
        return new Notifica(this);
    }
}
