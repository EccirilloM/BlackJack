package it.polimi.blackjackbe.command.impl;

import it.polimi.blackjackbe.command.Command;
import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TavoloStatus;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.implementation.TavoloServiceImplementation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
@Transactional
public class Double extends Command {
    private final TavoloServiceImplementation tavoloServiceImplementation;
    private final UserRepository userRepository;
    private final Command hit;
    private final Command stay;

    @Override
    public TavoloStatusResponse execute(Long userId, Map<String, Object> data) {
        Tavolo tavolo = tavoloServiceImplementation.getTavolo(userId);
        User user = tavolo.getPlayer();

        user.setSaldo(user.getSaldo() - tavolo.getPlotUser());

        tavolo.setPlotUser(tavolo.getPlotUser()*2);

        TavoloStatusResponse tavoloStatusResponse = hit.execute(userId, data);
        if(tavoloStatusResponse.getTavoloStatus() != TavoloStatus.CONTINUE)
            return tavoloStatusResponse;
        else
            return stay.execute(userId, data);
    }
}
