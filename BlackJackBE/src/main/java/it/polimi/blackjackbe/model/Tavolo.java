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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tavolo")
@Entity(name = "Tavolo")
public class Tavolo {
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


    @Column(nullable = false, updatable = false)
    private TipoTavolo tipoTavolo;

    @Column(nullable = false, updatable = false)
    private boolean vittoriaUser;

   @Transient
    private Double plotUser; //Quanto punta l'utente

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User player;

    @OneToMany(mappedBy = "tavolo", fetch = FetchType.LAZY)
    private List<Mano> mani;

    @Transient
    private List<Carta> carte;
    @Transient
    private List<Carta> carteSingolaManoPlayer;
    @Transient
    private List<Carta> carteSingolaManoDealer = new ArrayList<>();
    @Transient
    private Double totalWinning =0.0;
    @Transient
    private int order=0;

    public Tavolo(TavoloBuilder tavoloBuilder) {
        this.tavoloId = tavoloBuilder.getTavoloId();
        this.tipoTavolo = tavoloBuilder.getTipoTavolo();
        this.vittoriaUser = tavoloBuilder.isVittoriaUser();
        this.player = tavoloBuilder.getPlayer();
        this.carte = tavoloBuilder.getCarte();
        this.carteSingolaManoPlayer = tavoloBuilder.getCarteSingolaMano();
        this.carteSingolaManoDealer = tavoloBuilder.getCartaDealer();
    }


    public void initCarte(){
        if(carte==null||carte.isEmpty()){
            carte=new ArrayList<>();
            Random random=new Random();
            List<Carta> carte=new ArrayList<>();
            for(int i=0;i<tipoTavolo.getNumeroDiMazzi();i++){
                carte.addAll(List.of(
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
            while(!carte.isEmpty()){
                int index=random.nextInt(carte.size());
                this.carte.add(carte.remove(index));
            }
        }
    }

    @Transient
    public Carta pescaCarta(){
        if(carteSingolaManoPlayer==null||carteSingolaManoPlayer.isEmpty()){
            carteSingolaManoPlayer=new ArrayList<>();
            Carta carta=carte.remove(0);
            carteSingolaManoPlayer.add(carta);
            carta.setOrder(order++);
            return carta;
        }else if(carteSingolaManoPlayer.stream().mapToInt(Carta::getPunteggio).sum()>21){
            carteSingolaManoPlayer.clear();
            return null;
        }else{
            Carta carta=carte.remove(0);
            carteSingolaManoPlayer.add(carta);
            carta.setOrder(order++);
            return carta;
        }
    }

    @Transient
    public Carta pescaDealer(){
        Carta carta = carte.remove(0);
        carteSingolaManoDealer.add(carta);
        carta.setOrder(order++);
        return carta;
    }

    @Transient
    public int punteggioDealer(){
        return punteggio(carteSingolaManoDealer);
    }

    @Transient
    public int punteggioUtente(){
        return punteggio(carteSingolaManoPlayer);
    }

    private int punteggio(List<Carta> carte) {
        int punteggio=0;
        for(Carta carta:carte){
            punteggio+=carta.getPunteggio();
            while (punteggio>21 && carteSingolaManoDealer.stream().anyMatch(i -> i.getPunteggio()==11)){
                carteSingolaManoDealer.stream().filter(i -> i.getPunteggio()==11).findAny().get().setPunteggio(1);
                punteggio-=10;
            }
        }
        return punteggio;
    }

    public void clear(){
        carteSingolaManoPlayer.clear();
        carteSingolaManoDealer.clear();
    }


    public void end(){
        carteSingolaManoPlayer.clear();
        carteSingolaManoDealer=null;
    }
}
