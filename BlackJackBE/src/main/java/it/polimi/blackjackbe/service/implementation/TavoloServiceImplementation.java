package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.builder.ManoBuilder;
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
    private final UserRepository userRepository;
    private final ManoRepository manoRepository;


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

    public TavoloStatusResponse getTavoloStatusResponse(Tavolo tavolo, TavoloStatus tavoloStatus){
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

    public Tavolo getTavolo(Long userId){
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        Tavolo tavolo = SingletonTavoli.getInstance().getTable(user.get());

        if(tavolo.getCarteSingolaManoPlayer().size()<2)
            throw new IllegalStateException("The deal was not made");
        return tavolo;
    }

    public void processWin(Tavolo tavolo,TavoloStatus tavoloStatus){
        processWin(tavolo, tavoloStatus, false);
    }

    public void processWin(Tavolo tavolo, TavoloStatus tavoloStatus, boolean blackJack){
        User user = userRepository.findById(tavolo.getPlayer().getUserId()).get();
        User admin = userRepository.findByRuolo(Ruolo.ADMIN).get();
        Double importo = 0.0;
        if(tavoloStatus == TavoloStatus.PLAYER_WIN){
            Double vincita = tavolo.getPlotUser() * (blackJack? 2.5 : 2 );
            user.setSaldo(user.getSaldo() + vincita);
            admin.setSaldo(admin.getSaldo() - vincita);
            tavolo.setTotalWinning(tavolo.getTotalWinning()+vincita);
            importo = -vincita/2;
        }
        else if(tavoloStatus == TavoloStatus.PLAYER_LOSE){
            admin.setSaldo(admin.getSaldo() + tavolo.getPlotUser());
            tavolo.setTotalWinning(tavolo.getTotalWinning()-tavolo.getPlotUser());
            importo = tavolo.getPlotUser();
        }
        else if (tavoloStatus ==TavoloStatus.DRAW){
            user.setSaldo(user.getSaldo() + tavolo.getPlotUser());
        }


        Mano mano = new ManoBuilder()
                .tavolo(tavolo)
                .dataMano(LocalDateTime.now())
                .importo(importo)
                .build();

        manoRepository.save(mano);

    }
}
