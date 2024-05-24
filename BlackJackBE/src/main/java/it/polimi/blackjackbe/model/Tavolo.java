package it.polimi.blackjackbe.model;

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
                carte.addAll(List.of(new Carta("CUORI","2",2),
                        new Carta("CUORI","3",3),
                        new Carta("CUORI","4",4),
                        new Carta("CUORI","5",5),
                        new Carta("CUORI","6",6),
                        new Carta("CUORI","7",7),
                        new Carta("CUORI","8",8),
                        new Carta("CUORI","9",9),
                        new Carta("CUORI","10",10),
                        new Carta("CUORI","J",10),
                        new Carta("CUORI","Q",10),
                        new Carta("CUORI","K",10),
                        new Carta("CUORI","A",11),
                        new Carta("QUADRI","2",2),
                        new Carta("QUADRI","3",3),
                        new Carta("QUADRI","4",4),
                        new Carta("QUADRI","5",5),
                        new Carta("QUADRI","6",6),
                        new Carta("QUADRI","7",7),
                        new Carta("QUADRI","8",8),
                        new Carta("QUADRI","9",9),
                        new Carta("QUADRI","10",10),
                        new Carta("QUADRI","J",10),
                        new Carta("QUADRI","Q",10),
                        new Carta("QUADRI","K",10),
                        new Carta("QUADRI","A",11),
                        new Carta("FIORI","2",2),
                        new Carta("FIORI","3",3),
                        new Carta("FIORI","4",4),
                        new Carta("FIORI","5",5),
                        new Carta("FIORI","6",6),
                        new Carta("FIORI","7",7),
                        new Carta("FIORI","8",8),
                        new Carta("FIORI","9",9),
                        new Carta("FIORI","10",10),
                        new Carta("FIORI","J",10),
                        new Carta("FIORI","Q",10),
                        new Carta("FIORI","K",10),
                        new Carta("FIORI","A",11),
                        new Carta("PICCHE","2",2),
                        new Carta("PICCHE","3",3),
                        new Carta("PICCHE","4",4),
                        new Carta("PICCHE","5",5),
                        new Carta("PICCHE","6",6),
                        new Carta("PICCHE","7",7),
                        new Carta("PICCHE","8",8),
                        new Carta("PICCHE","9",9),
                        new Carta("PICCHE","10",10),
                        new Carta("PICCHE","J",10),
                        new Carta("PICCHE","Q",10),
                        new Carta("PICCHE","K",10),
                        new Carta("PICCHE","A",11)));
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
