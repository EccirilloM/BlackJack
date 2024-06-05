package it.polimi.blackjackbe.builder;

import it.polimi.blackjackbe.model.Ricarica;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Design pattern builder. Classe Builder dove vengono riportati esattamente tutti
 * gli attributi del model Tabacchi e un rispettivo metodo (setter) per ognuno di loro.
 */
@Getter
@NoArgsConstructor
public class TabacchiBuilder {
    // Attributi del model Tabacchi
    private Long tabacchiId;
    private String nome;
    private Double lat;
    private Double lng;
    private List<Ricarica> ricariche; //Ricariche effettuate nel singolo tabacchi
    private User economo; //Economo che gestisce il tabacchi

    /**
     * Chiamato prima del metodo build(), per settare l'attributo tabacchiId.
     * @param tabacchiId Valore dell'id univoco del tabacchi.
     * @return Un'istanza della classe stessa.
     */
    public TabacchiBuilder tabacchiId(Long tabacchiId) {
        this.tabacchiId = tabacchiId;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo nome.
     * @param nome Nome del tabacchi.
     * @return Un'istanza della classe stessa.
     */
    public TabacchiBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo lat.
     * @param lat Latitudine del tabacchi.
     * @return Un'istanza della classe stessa.
     */
    public TabacchiBuilder lat(Double lat) {
        this.lat = lat;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo lng.
     * @param lng Longitudine del tabacchi.
     * @return Un'istanza della classe stessa.
     */
    public TabacchiBuilder lng(Double lng) {
        this.lng = lng;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo ricariche.
     * @param ricariche Lista delle ricariche effettuate nel tabacchi.
     * @return Un'istanza della classe stessa.
     */
    public TabacchiBuilder ricariche(List<Ricarica> ricariche) {
        this.ricariche = ricariche;
        return this;
    }

    /**
     * Chiamato prima del metodo build(), per settare l'attributo economo.
     * @param economo Economo che gestisce il tabacchi.
     * @return Un'istanza della classe stessa.
     */
    public TabacchiBuilder economo(User economo) {
        this.economo = economo;
        return this;
    }

    /**
     * Metodo che costruisce l'oggetto {@link Tabacchi} con tutti i dati settati in precedenza.
     * @return Un'istanza di Tabacchi a cui viene passato, tramite il costruttore, l'istanza di questa classe.
     */
    public Tabacchi build() {
        return new Tabacchi(this);
    }
}
