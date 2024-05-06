package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.request.MessaggioRequest;
import it.polimi.blackjackbe.dto.response.GetAllMessagesByTipoTavoloResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.Messaggio;
import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.TipoTavolo;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.MessaggioRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.MessaggioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessaggioServiceImplementation implements MessaggioService {

    private final UserRepository userRepository;
    private final MessaggioRepository messaggioRepository;

    @Override
    public List<GetAllMessagesByTipoTavoloResponse> getAllMessagesByTipoTavolo(String tipoTavoloRequest) {
        TipoTavolo tipoTavolo = switch (tipoTavoloRequest) {
            case "BASE" -> TipoTavolo.BASE;
            case "PREMIUM" -> TipoTavolo.PREMIUM;
            case "VIP" -> TipoTavolo.VIP;
            case "EXCLUSIVE" -> TipoTavolo.EXCLUSIVE;
            default -> throw new BadRequestException("Tipo Tavolo non valido");
        };

        List<Messaggio> messaggi = messaggioRepository.findAllByTipoTavolo(tipoTavolo);

        List<GetAllMessagesByTipoTavoloResponse> response = new ArrayList<>();

        for (Messaggio messaggio : messaggi) {
            response.add(new GetAllMessagesByTipoTavoloResponse(
                    messaggio.getTesto(),
                    messaggio.getCreatedAt(),
                    messaggio.getUser().getUsername()));
        }

        return response;
    }


    @Override
    public void inviaMessaggio(MessaggioRequest messaggioRequest) {
        if(messaggioRequest.getTesto().isEmpty() || messaggioRequest.getTesto().isBlank()) {
            throw new IllegalArgumentException("Testo Messaggio non valido");
        }

        if(messaggioRequest.getMittenteId() == null) {
            throw new IllegalArgumentException("Id Utente non valido");
        }

        TipoTavolo tipoTavolo = switch (messaggioRequest.getTipoTavolo()) {
            case "BASE" -> TipoTavolo.BASE;
            case "PREMIUM" -> TipoTavolo.PREMIUM;
            case "VIP" -> TipoTavolo.VIP;
            case "EXCLUSIVE" -> TipoTavolo.EXCLUSIVE;
            default -> throw new BadRequestException("Tipo Tavolo non valido");
        };

        Optional<User> userExists = userRepository.findByUserId(messaggioRequest.getMittenteId());

        if(userExists.isEmpty()){
            throw new NotFoundException("Utente non trovato");
        }

        if(!userExists.get().getRuolo().equals(Ruolo.PLAYER)){
            throw new BadRequestException("Ruolo non valido");
        }

        Messaggio messaggio = Messaggio.builder()
                .testo(messaggioRequest.getTesto())
                .user(userExists.get())
                .createdAt(LocalDateTime.now())
                .tipoTavolo(tipoTavolo)
                .build();
        messaggioRepository.save(messaggio);
    }


}
