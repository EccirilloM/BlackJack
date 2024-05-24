package it.polimi.blackjackbe.command.implementation;


import it.polimi.blackjackbe.command.Command;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import it.polimi.blackjackbe.singleton.SingletonTavoli;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Component
@Transactional
public class Deal extends Command {
    private final UserRepository userRepository;
    private final TavoloServiceImplementation tavoloServiceImplementation;

    @Override
    public TavoloStatusResponse execute(Long userId, Map<String, Object> data) {
        double plot = 0.0;
        try {
            plot = java.lang.Double.valueOf(data.get("plot").toString());
        }catch (NumberFormatException | NullPointerException e){
            throw new BadRequestException("Plot is invalid");
        }
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
            tavoloServiceImplementation.processWin(tavolo, tavoloStatus, true);
        }

        TavoloStatusResponse tavoloStatusResponse = tavoloServiceImplementation.getTavoloStatusResponse(tavolo, tavoloStatus);
        if(tavoloStatusResponse.getTavoloStatus() != TavoloStatus.CONTINUE)
            tavolo.clear();
        return tavoloStatusResponse;
    }


}
