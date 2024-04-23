package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.request.CreaTabacchiRequest;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.TabacchiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .lng(request.getLng())
                .lat(request.getLat())
                .economo(economo.get())
                .build();
        tabacchiRepository.save(tabacchi);
    }
}
