package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.builder.TavoloBuilder;
import it.polimi.blackjackbe.dto.request.EndTavoloRequest;
import it.polimi.blackjackbe.dto.response.CartaResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.model.Carta;
import it.polimi.blackjackbe.model.Tavolo;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.TavoloRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.TavoloService;
import it.polimi.blackjackbe.strategy.context.StrategyManager;
import it.polimi.blackjackbe.strategy.operation.ChiediCarta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TavoloServiceImplementation implements TavoloService {
    private final TavoloRepository tavoloRepository;
    private final StrategyManager strategyManager;
    private final UserRepository userRepository;

    @Override
    public CartaResponse chiediCarta(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        Carta carta = strategyManager.executeStrategy(user.get(), new ChiediCarta());
        CartaResponse response = new CartaResponse(carta.getSeme(), carta.getValore(), carta.getPunteggio());
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

        Tavolo tavolo = new TavoloBuilder()
                .tipoTavolo(TipoTavolo.valueOf(tipoTavolo))
                .player(user.get())
                .build();

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
}
