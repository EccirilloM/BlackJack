package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.request.CreaTabacchiRequest;
import it.polimi.blackjackbe.dto.response.TabacchiResponse;
import it.polimi.blackjackbe.dto.response.UserResponse;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.TabacchiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TabacchiServiceImplementation implements TabacchiService {


    private final TabacchiRepository tabacchiRepository;
    private final UserRepository userRepository;

    @Override
    public void creaTabacchi(CreaTabacchiRequest request) {
        if(request.getNomeTabacchi().isEmpty() || request.getNomeTabacchi().isBlank()) {
            throw new IllegalArgumentException("Nome tabacchi non valido");
        }

        if(request.getLat().isInfinite() || request.getLat().isNaN()) {
            throw new IllegalArgumentException("Latitudine non valida");
        }

        if (request.getLng().isInfinite() || request.getLng().isNaN()) {
            throw new IllegalArgumentException("Longitudine non valida");
        }

        if(request.getEconomoId() < 1){
            throw new IllegalArgumentException("Id economo non valido");
        }

        Optional<User> economo = userRepository.findById(request.getEconomoId());

        if(economo.isEmpty()) {
            throw new IllegalArgumentException("Economo non trovato");
        }

        Tabacchi tabacchi = Tabacchi.builder()
                .nome(request.getNomeTabacchi())
                .lng(Double.valueOf(request.getLng()))
                .lat(Double.valueOf(request.getLat()))
                .economo(economo.get())
                .build();
        tabacchiRepository.save(tabacchi);
    }

    @Override
    public List<TabacchiResponse> getAllTabacchi() {
        //Prendo dal db tutti gli utenti.
        List<Tabacchi> tabacchis = tabacchiRepository.findAll();


        //Inizializzo la variabile di risposta.
        List<TabacchiResponse> response = new ArrayList<>();

        //Per ogni utente, aggiungo all'array di risposta i dati.
        for(Tabacchi tabacchi: tabacchis) {
            response.add(new TabacchiResponse(
                    tabacchi.getTabacchiId(),
                    tabacchi.getNome(),
                    tabacchi.getLat(),
                    tabacchi.getLng(),
                    tabacchi.getEconomo().getUserId()
            ));
        }

        return response;
    }

    @Override
    public void deleteTabacchi(Long tabacchiId) {
        Optional<Tabacchi> tabacchi = tabacchiRepository.findById(tabacchiId);

        if(tabacchi.isEmpty()) {
            throw new NotFoundException("Tabacchi non trovato");
        }

        tabacchiRepository.delete(tabacchi.get());
    }
}
