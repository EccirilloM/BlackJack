package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.builder.NotificaBuilder;
import it.polimi.blackjackbe.builder.RicaricaBuilder;
import it.polimi.blackjackbe.dto.request.RichiestaRicaricaRequest;
import it.polimi.blackjackbe.dto.response.GetAllRichiesteRicaricaSaldoResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.*;
import it.polimi.blackjackbe.repository.NotificaRepository;
import it.polimi.blackjackbe.repository.RicaricaRepository;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.RicaricaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class RicaricaServiceImplementation implements RicaricaService {
    private final RicaricaRepository ricaricaRepository;
    private final UserRepository userRepository;
    private final TabacchiRepository tabacchiRepository;
    private final NotificaRepository notificaRepository;

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

        Ricarica ricarica = new RicaricaBuilder()
                .richiesta(true)
                .dataRichiesta(LocalDateTime.now())
                .importo(request.getImporto())
                .player(userExists.get())
                .tabacchi(tabacchiExists.get())
                .build();
        ricaricaRepository.save(ricarica);
    }

    @Override
    public List<GetAllRichiesteRicaricaSaldoResponse> getAllRichiesteRicarica(Long userId) {
        if(userId < 1){
            throw new BadRequestException("Id non Valido");
        }

        Optional<User> userExists = userRepository.findByUserId(userId);

        if(userExists.isEmpty()){
            throw new NotFoundException("Utente non trovato");
        }

        if(!userExists.get().getRuolo().equals(Ruolo.ECONOMO)){
            throw new BadRequestException("Ruolo non valido");
        }

        List<Tabacchi> tabacchi = tabacchiRepository.findAllByEconomo(userExists.get());

        List<Ricarica> ricariche = new ArrayList<>();

        for(Tabacchi t : tabacchi){
            ricariche.addAll(ricaricaRepository.findAllByTabacchi(t));
        }

        List<GetAllRichiesteRicaricaSaldoResponse> response = new ArrayList<>();

        for(Ricarica r : ricariche){
            if(!r.isAccettata() && r.isRichiesta()){
                response.add(new GetAllRichiesteRicaricaSaldoResponse(
                        r.getRicaricaId(),
                        r.getPlayer().getUserId(),
                        r.getPlayer().getNome(),
                        r.getPlayer().getCognome(),
                        r.getPlayer().getEmail(),
                        r.getPlayer().getUsername(),
                        r.getDataRichiesta(),
                        r.getImporto()
                ));
            }
        }

        return response;
    }

    @Override
    public void accettaRicarica(Long ricaricaId, Long playerId) {
        if(ricaricaId < 1){
            throw new BadRequestException("Id non Valido");
        }

        Optional<Ricarica> ricaricaExists = ricaricaRepository.findById(ricaricaId);

        if(ricaricaExists.isEmpty()){
            throw new NotFoundException("Ricarica non trovata");
        }

        Ricarica ricarica = ricaricaExists.get();

        ricarica.setRichiesta(false);
        ricarica.setAccettata(true);
        ricaricaRepository.save(ricarica);

        if(playerId < 1){
            throw new BadRequestException("Id player non Valido");
        }

        Optional<User> playerExists = userRepository.findById(playerId);

        if(playerExists.isEmpty()){
            throw new NotFoundException("Player non trovato");
        }

        playerExists.get().setSaldo(playerExists.get().getSaldo() + ricarica.getImporto());

        userRepository.save(playerExists.get());

        notificaRepository.save(new NotificaBuilder()
                .data(LocalDateTime.now())
                .testo("Ricarica di " + ricarica.getImporto() + "€ accettata")
                .player(playerExists.get())
                .build());
    }

    @Override
    public void rifiutaRicarica(Long ricaricaId) {
        if(ricaricaId < 1){
            throw new BadRequestException("Id non Valido");
        }

        Optional<Ricarica> ricaricaExists = ricaricaRepository.findById(ricaricaId);

        if(ricaricaExists.isEmpty()){
            throw new NotFoundException("Ricarica non trovata");
        }

        Ricarica ricarica = ricaricaExists.get();

        ricaricaRepository.delete(ricarica);

        notificaRepository.save(new NotificaBuilder()
                .data(LocalDateTime.now())
                .testo("Ricarica di " + ricarica.getImporto() + "€ rifiutata")
                .player(ricarica.getPlayer())
                .build());
    }



}
