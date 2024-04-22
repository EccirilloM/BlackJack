package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.request.RichiestaRicaricaRequest;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.Ricarica;
import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.RicaricaRepository;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.RicaricaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class RicaricaServiceImplementation implements RicaricaService {
    private final RicaricaRepository ricaricaRepository;
    private final UserRepository userRepository;
    private final TabacchiRepository tabacchiRepository;

    @Override
    public void richiediRicarica(Long userId, RichiestaRicaricaRequest request) {
        if (request.getImporto()< 0) {
            throw new BadRequestException("Importo non valido");
        }

        if(userId < 1){
            throw new BadRequestException("Id non Valido");
        }

        Optional<User> userExists = userRepository.findByUserId(userId);

        if(userExists.isEmpty()){
            throw new NotFoundException("Utente non trovato");
        }

        if(!userExists.get().getRuolo().equals(Ruolo.PLAYER)){
            throw new BadRequestException("Ruolo non valido");
        }

        if(request.getTabacchiId() < 1){
            throw new BadRequestException("Id non Valido");
        }

        Optional<Tabacchi> tabacchiExists = tabacchiRepository.findById(request.getTabacchiId());

        if(tabacchiExists.isEmpty()){
            throw new NotFoundException("Tabacchi non trovato");
        }

        Ricarica ricarica = Ricarica.builder()
                .richiesta(true)
                .importo(request.getImporto())
                .player(userExists.get())
                .tabacchi(tabacchiExists.get())
                .build();
        ricaricaRepository.save(ricarica);
    }



}
