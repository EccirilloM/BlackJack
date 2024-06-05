package it.polimi.blackjackbe.model;

import it.polimi.blackjackbe.builder.CartaBuilder;
import it.polimi.blackjackbe.builder.TavoloBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model che rappresenta un tavolo nel gioco del Blackjack.
 * Questa classe utilizza il pattern Builder per creare oggetti di tipo Tavolo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tavolo")
@Entity(name = "Tavolo")
public class Tavolo {

    /**
     * Identificatore univoco per ogni tavolo.
     * Configurato con autoincremento utilizzando una sequenza.
     */
    @Id
    @SequenceGenerator(
            name = "tavolo_sequence",
            sequenceName = "tavolo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tavolo_sequence"
    )
    @Column(updatable = false, nullable = false, name = "tavolo_id")
    private Long tavoloId;

    /**
     * Tipo di tavolo.
     * Non può essere modificato una volta impostato.
     */
    @Column(nullable = false, updatable = false)
    private TipoTavolo tipoTavolo;

    /**
     * Importo puntato dall'utente.
     */
    @Transient
    private Double plotUser;

    /**
     * Utente che gioca al tavolo.
     * Relazione Many-to-One con la classe {@link User}.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User player;

    /**
     * Mani giocate al tavolo.
     * Relazione One-to-Many con la classe {@link Mano}.
     */
    @OneToMany(mappedBy = "tavolo", fetch = FetchType.LAZY)
    private List<Mano> mani;

    /**
     * Carte disponibili al tavolo.
     * Questo campo è transiente e non verrà salvato nel database.
     */
    @Transient
    private List<Carta> carte;

    /**
     * Carte della mano corrente del giocatore.
     * Questo campo è transiente e non verrà salvato nel database.
     */
    @Transient
    private List<Carta> carteSingolaManoPlayer;

    /**
     * Carte della mano corrente del dealer.
     * Questo campo è transiente e non verrà salvato nel database.
     */
    @Transient
    private List<Carta> carteSingolaManoDealer = new ArrayList<>();

    /**
     * Vincite totali del tavolo.
     * Questo campo è transiente e non verrà salvato nel database.
     */
    @Transient
    private Double totalWinning = 0.0;

    /**
     * Ordine delle carte.
     * Questo campo è transiente e non verrà salvato nel database.
     */
    @Transient
    private int order = 0;

    /**
     * Costruttore che utilizza il builder per assegnare i valori agli attributi del tavolo.
     * @param tavoloBuilder Dati appena settati tramite il pattern builder {@link TavoloBuilder}.
     */
    public Tavolo(TavoloBuilder tavoloBuilder) {
        this.tavoloId = tavoloBuilder.getTavoloId();
        this.tipoTavolo = tavoloBuilder.getTipoTavolo();
        this.player = tavoloBuilder.getPlayer();
        this.carte = tavoloBuilder.getCarte();
        this.carteSingolaManoPlayer = tavoloBuilder.getCarteSingolaMano();
        this.carteSingolaManoDealer = tavoloBuilder.getCartaDealer();
    }

    /**
     * Inizializza il mazzo di carte (per rendere il gioco più reale possibile) mescolandole casualmente.
     * Aggiunge le carte di vari semi al mazzo per ogni mazzo disponibile nel tavolo e le mescola.
     */
    public void initCarte() {
        if (carte == null || carte.isEmpty()) {
            carte = new ArrayList<>();
            Random random = new Random();
            List<Carta> carte = new ArrayList<>();
            // Aggiunge le carte dei vari semi al mazzo per ogni mazzo disponibile nel tavolo
            for (int i = 0; i < tipoTavolo.getNumeroDiMazzi(); i++) {
                // Aggiungi le carte di tutti i semi (CUORI, QUADRI, FIORI, PICCHE)
                carte.addAll(List.of(
                        // Esempio di aggiunta di carte (aggiungere tutte le carte necessarie)
                        new CartaBuilder().seme("CUORI").valore("2").punteggio(2).build(),
                        new CartaBuilder().seme("CUORI").valore("3").punteggio(3).build(),
                        new CartaBuilder().seme("CUORI").valore("4").punteggio(4).build(),
                        new CartaBuilder().seme("CUORI").valore("5").punteggio(5).build(),
                        new CartaBuilder().seme("CUORI").valore("6").punteggio(6).build(),
                        new CartaBuilder().seme("CUORI").valore("7").punteggio(7).build(),
                        new CartaBuilder().seme("CUORI").valore("8").punteggio(8).build(),
                        new CartaBuilder().seme("CUORI").valore("9").punteggio(9).build(),
                        new CartaBuilder().seme("CUORI").valore("10").punteggio(10).build(),
                        new CartaBuilder().seme("CUORI").valore("J").punteggio(10).build(),
                        new CartaBuilder().seme("CUORI").valore("Q").punteggio(10).build(),
                        new CartaBuilder().seme("CUORI").valore("K").punteggio(10).build(),
                        new CartaBuilder().seme("CUORI").valore("A").punteggio(11).build(),
                        new CartaBuilder().seme("QUADRI").valore("2").punteggio(2).build(),
                        new CartaBuilder().seme("QUADRI").valore("3").punteggio(3).build(),
                        new CartaBuilder().seme("QUADRI").valore("4").punteggio(4).build(),
                        new CartaBuilder().seme("QUADRI").valore("5").punteggio(5).build(),
                        new CartaBuilder().seme("QUADRI").valore("6").punteggio(6).build(),
                        new CartaBuilder().seme("QUADRI").valore("7").punteggio(7).build(),
                        new CartaBuilder().seme("QUADRI").valore("8").punteggio(8).build(),
                        new CartaBuilder().seme("QUADRI").valore("9").punteggio(9).build(),
                        new CartaBuilder().seme("QUADRI").valore("10").punteggio(10).build(),
                        new CartaBuilder().seme("QUADRI").valore("J").punteggio(10).build(),
                        new CartaBuilder().seme("QUADRI").valore("Q").punteggio(10).build(),
                        new CartaBuilder().seme("QUADRI").valore("K").punteggio(10).build(),
                        new CartaBuilder().seme("QUADRI").valore("A").punteggio(11).build(),
                        new CartaBuilder().seme("FIORI").valore("2").punteggio(2).build(),
                        new CartaBuilder().seme("FIORI").valore("3").punteggio(3).build(),
                        new CartaBuilder().seme("FIORI").valore("4").punteggio(4).build(),
                        new CartaBuilder().seme("FIORI").valore("5").punteggio(5).build(),
                        new CartaBuilder().seme("FIORI").valore("6").punteggio(6).build(),
                        new CartaBuilder().seme("FIORI").valore("7").punteggio(7).build(),
                        new CartaBuilder().seme("FIORI").valore("8").punteggio(8).build(),
                        new CartaBuilder().seme("FIORI").valore("9").punteggio(9).build(),
                        new CartaBuilder().seme("FIORI").valore("10").punteggio(10).build(),
                        new CartaBuilder().seme("FIORI").valore("J").punteggio(10).build(),
                        new CartaBuilder().seme("FIORI").valore("Q").punteggio(10).build(),
                        new CartaBuilder().seme("FIORI").valore("K").punteggio(10).build(),
                        new CartaBuilder().seme("FIORI").valore("A").punteggio(11).build(),
                        new CartaBuilder().seme("PICCHE").valore("2").punteggio(2).build(),
                        new CartaBuilder().seme("PICCHE").valore("3").punteggio(3).build(),
                        new CartaBuilder().seme("PICCHE").valore("4").punteggio(4).build(),
                        new CartaBuilder().seme("PICCHE").valore("5").punteggio(5).build(),
                        new CartaBuilder().seme("PICCHE").valore("6").punteggio(6).build(),
                        new CartaBuilder().seme("PICCHE").valore("7").punteggio(7).build(),
                        new CartaBuilder().seme("PICCHE").valore("8").punteggio(8).build(),
                        new CartaBuilder().seme("PICCHE").valore("9").punteggio(9).build(),
                        new CartaBuilder().seme("PICCHE").valore("10").punteggio(10).build(),
                        new CartaBuilder().seme("PICCHE").valore("J").punteggio(10).build(),
                        new CartaBuilder().seme("PICCHE").valore("Q").punteggio(10).build(),
                        new CartaBuilder().seme("PICCHE").valore("K").punteggio(10).build(),
                        new CartaBuilder().seme("PICCHE").valore("A").punteggio(11).build()
                ));
            }
            // Mescola le carte in modo casuale
            while(!carte.isEmpty()){
                int index=random.nextInt(carte.size());
                this.carte.add(carte.remove(index));
            }
        }
    }

    /**
     * Pesca una carta per il giocatore.
     * @return La carta pescata.
     */
    @Transient
    public Carta pescaCarta() {
        if (carte.isEmpty()) {
            initCarte();
        }
        if (carteSingolaManoPlayer == null || carteSingolaManoPlayer.isEmpty()) {
            carteSingolaManoPlayer = new ArrayList<>();
            Carta carta = carte.remove(0);
            carteSingolaManoPlayer.add(carta);
            carta.setOrder(order++);
            return carta;
        } else if (carteSingolaManoPlayer.stream().mapToInt(Carta::getPunteggio).sum() > 21) {
            carteSingolaManoPlayer.clear();
            return null;
        } else {
            Carta carta = carte.remove(0);
            carteSingolaManoPlayer.add(carta);
            carta.setOrder(order++);
            return carta;
        }
    }

    /**
     * Pesca una carta per il dealer.
     * @return La carta pescata.
     */
    @Transient
    public Carta pescaDealer() {
        if (carte.isEmpty()) {
            initCarte();
        }
        Carta carta = carte.remove(0);
        carteSingolaManoDealer.add(carta);
        carta.setOrder(order++);
        return carta;
    }

    /**
     * Calcola il punteggio del dealer.
     * @return Il punteggio del dealer.
     */
    @Transient
    public int punteggioDealer() {
        return punteggio(carteSingolaManoDealer);
    }

    /**
     * Calcola il punteggio del giocatore.
     * @return Il punteggio del giocatore.
     */
    @Transient
    public int punteggioUtente() {
        return punteggio(carteSingolaManoPlayer);
    }

    /**
     * Calcola il punteggio totale delle carte.
     * @param carte Le carte di cui calcolare il punteggio.
     * @return Il punteggio totale.
     */
    private int punteggio(List<Carta> carte) {
        int punteggio = 0;
        for (Carta carta : carte) {
            punteggio += carta.getPunteggio();
            while (punteggio > 21 && carte.stream().anyMatch(i -> i.getPunteggio() == 11)) {
                carte.stream().filter(i -> i.getPunteggio() == 11).findAny().get().setPunteggio(1);
                punteggio -= 10;
            }
        }
        return punteggio;
    }

    /**
     * Resetta le carte della mano corrente del giocatore e del dealer.
     */
    public void clear() {
        carteSingolaManoPlayer.clear();
        carteSingolaManoDealer.clear();
    }

    /**
     * Termina il gioco e resetta le carte della mano corrente del giocatore.
     */
    public void end() {
        carteSingolaManoPlayer.clear();
        carteSingolaManoDealer = null;
    }
}
