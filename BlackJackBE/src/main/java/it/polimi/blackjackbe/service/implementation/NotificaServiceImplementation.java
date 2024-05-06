package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.response.NotificaResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.model.Notifica;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.NotificaRepository;
import it.polimi.blackjackbe.repository.UserRepository;

import it.polimi.blackjackbe.service.definition.NotificaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificaServiceImplementation implements NotificaService {

    private final NotificaRepository notificaRepository;
    private final UserRepository userRepository;

    @Override
    public List<NotificaResponse> getAllByUserId(Long userId) {

        if(userId < 1) {
            throw new BadRequestException("Id non valido");
        }

        Optional<User> utenteExists = userRepository.findById(userId);

        if(utenteExists.isEmpty()) {
            throw new BadRequestException("Utente non trovato");
        }

        List<Notifica> notifiche = notificaRepository.findAllByPlayer(utenteExists.get());

        List<NotificaResponse> response = new ArrayList<>();

        for(Notifica notifica : notifiche) {
            response.add(new NotificaResponse(notifica.getData(), notifica.getTesto()));
        }

        return response;
    }
}
