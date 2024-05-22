package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.request.EndTavoloRequest;
import it.polimi.blackjackbe.dto.response.CartaResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.model.*;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.TavoloRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.service.definition.TavoloService;
import it.polimi.blackjackbe.singleton.SingletonTavoli;
import it.polimi.blackjackbe.strategy.context.StrategyManager;
import it.polimi.blackjackbe.strategy.operation.ChiediCarta;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TavoloServiceImplementation implements TavoloService {
    private final TavoloRepository tavoloRepository;
    private final StrategyManager strategyManager;
    private final UserRepository userRepository;
    private final ManoRepository manoRepository;


    @Override
    public CartaResponse chiediCarta(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        Carta carta = strategyManager.executeStrategy(user.get(), new ChiediCarta());
        CartaResponse response = new CartaResponse(carta.getSeme(), carta.getValore(), carta.getPunteggio(), carta.getOrder());
        return response;
    }

    @Override
    public void init(String tipoTavolo, Long userId) {
        if(userId == null || userId <= 0) {
            throw new BadRequestException("User id is required");
        }

        if(tipoTavolo == null || tipoTavolo.isEmpty()) {
            throw new BadRequestException("Table type is required");
        }

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        Tavolo tavolo = SingletonTavoli.getInstance().createTable(user.get(), TipoTavolo.valueOf(tipoTavolo));

        tavoloRepository.save(tavolo);

        tavolo.initCarte();
    }

    @Override
    public void end(Long userId, EndTavoloRequest request) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        Optional<Tavolo> tavolo = tavoloRepository.findByPlayer(user.get());
        if (tavolo.isEmpty()) {
            throw new BadRequestException("Table not found");
        }

        tavolo.get().setVittoriaUser(request.getPlotUser() > 0);
        tavoloRepository.save(tavolo.get());

        tavolo.get().end();
    }

    @Override
    public TavoloStatusResponse deal(Long userId, Double plot){
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        if(user.get().getSaldo() < plot)
            throw new IllegalStateException("The plot is higer then the balance");

        user.get().setSaldo(user.get().getSaldo() - plot);

        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user.get());
        tavolo.setPlotUser(plot);

        tavolo.pescaDealer();
        tavolo.pescaCarta();
        tavolo.pescaCarta();

        TavoloStatus tavoloStatus = TavoloStatus.CONTINUE;
        if(tavolo.punteggioUtente() == 21){
            tavoloStatus = TavoloStatus.PLAYER_WIN;
            processWin(tavolo, tavoloStatus, true);
        }

        TavoloStatusResponse tavoloStatusResponse = getTavoloStatusResponse(tavolo, tavoloStatus);
        if(tavoloStatusResponse.getTavoloStatus() != TavoloStatus.CONTINUE)
            clearHands(tavolo);
        return tavoloStatusResponse;
    }

    @Override
    public TavoloStatusResponse hit(Long userId){
        Tavolo tavolo = getTavolo(userId);

        tavolo.pescaCarta();
        TavoloStatus tavoloStatus = TavoloStatus.CONTINUE;
        if(tavolo.punteggioUtente()==21){
            tavoloStatus = TavoloStatus.PLAYER_WIN;
        } else if (tavolo.punteggioUtente()>21) {
            tavoloStatus = TavoloStatus.PLAYER_LOSE;
        }
        TavoloStatusResponse tavoloStatusResponse = getTavoloStatusResponse(tavolo, tavoloStatus);
        if(tavoloStatusResponse.getTavoloStatus() != TavoloStatus.CONTINUE){
            processWin(tavolo, tavoloStatus);
            clearHands(tavolo);
        }
        return tavoloStatusResponse;
    }

    @Override
    public TavoloStatusResponse doubleCommand(Long userId){
        Tavolo tavolo = getTavolo(userId);
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        if(user.get().getSaldo() < tavolo.getPlotUser())
            throw new IllegalStateException("The plot is higer then the balance");

        user.get().setSaldo(user.get().getSaldo() - tavolo.getPlotUser());

        tavolo.setPlotUser(tavolo.getPlotUser()*2);

        TavoloStatusResponse tavoloStatusResponse = hit(userId);
        if(tavoloStatusResponse.getTavoloStatus() != TavoloStatus.CONTINUE)
            return tavoloStatusResponse;
        else
            return stay(userId);
    }

    @Override
    public TavoloStatusResponse stay(Long userId){
        Tavolo tavolo = getTavolo(userId);

        var punteggioUtente = tavolo.punteggioUtente();
        var punteggioDealer = stayDealer(tavolo);
        TavoloStatus tavoloStatus;
        if(punteggioDealer>21){
            tavoloStatus = TavoloStatus.PLAYER_WIN;
        }else{
            if(Math.abs(punteggioUtente-21) < Math.abs(punteggioDealer-21)){
                tavoloStatus = TavoloStatus.PLAYER_WIN;
                processWin(tavolo, tavoloStatus);
            }else if(Math.abs(punteggioUtente-21)== Math.abs(punteggioDealer-21)) {
                tavoloStatus = TavoloStatus.DRAW;
                processWin(tavolo, tavoloStatus);
            }else{
                tavoloStatus = TavoloStatus.PLAYER_LOSE;
                processWin(tavolo, tavoloStatus);
            }
        }
        TavoloStatusResponse tavoloStatusResponse = getTavoloStatusResponse(tavolo, tavoloStatus);
        clearHands(tavolo);
        return tavoloStatusResponse;
    }

    private void clearHands(Tavolo tavolo){
        tavolo.getCarteSingolaManoPlayer().clear();
        tavolo.getCarteSingolaManoDealer().clear();
    }

    private TavoloStatusResponse getTavoloStatusResponse(Tavolo tavolo, TavoloStatus tavoloStatus){
        User user = userRepository.findById(tavolo.getPlayer().getUserId()).get();
        return new TavoloStatusResponse(
                tavolo.getCarteSingolaManoPlayer().stream().map(carta-> new CartaResponse(carta.getSeme(), carta.getValore(), carta.getPunteggio(), carta.getOrder())).toList(),
                tavolo.punteggioUtente(),
                tavolo.getCarteSingolaManoDealer().stream().map(carta-> new CartaResponse(carta.getSeme(), carta.getValore(), carta.getPunteggio(), carta.getOrder())).toList(),
                tavolo.punteggioDealer(),
                tavoloStatus,
                user.getSaldo(),
                tavolo.getTotalWinning()
        );
    }

    private Tavolo getTavolo(Long userId){
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user.get());

        if(tavolo.getCarteSingolaManoPlayer().size()<2)
            throw new IllegalStateException("The deal was not made");
        return tavolo;
    }

    private void processWin(Tavolo tavolo,TavoloStatus tavoloStatus){
        processWin(tavolo, tavoloStatus, false);
    }

    private void processWin(Tavolo tavolo, TavoloStatus tavoloStatus, boolean blackJack){
        User user = userRepository.findById(tavolo.getPlayer().getUserId()).get();
        User admin = userRepository.findByRuolo(Ruolo.ADMIN).get();
        Double importo = 0.0;
        if(tavoloStatus == TavoloStatus.PLAYER_WIN){
            Double vincita = tavolo.getPlotUser() * (blackJack? 2.5 : 2 );
            user.setSaldo(user.getSaldo() + vincita);
            admin.setSaldo(admin.getSaldo() - vincita);
            tavolo.setTotalWinning(tavolo.getTotalWinning()+vincita);
            importo = -vincita;
        }
        else if(tavoloStatus == TavoloStatus.PLAYER_LOSE){
            admin.setSaldo(admin.getSaldo() + tavolo.getPlotUser());
            tavolo.setTotalWinning(tavolo.getTotalWinning()-tavolo.getPlotUser());
            importo = tavolo.getPlotUser();
        }
        else if (tavoloStatus ==TavoloStatus.DRAW){
            user.setSaldo(user.getSaldo() + tavolo.getPlotUser());
        }


        Mano mano = new Mano(tavolo, LocalDateTime.now(), importo);

        manoRepository.save(mano);

    }

    private int stayDealer(Tavolo tavolo) {
        while(tavolo.punteggioDealer() <17) {
            tavolo.punteggioDealer();
        }
        return tavolo.punteggioDealer();
    }
}
