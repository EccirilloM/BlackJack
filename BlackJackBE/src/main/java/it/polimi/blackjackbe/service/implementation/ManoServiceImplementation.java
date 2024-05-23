package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.response.GetAllManiResponse;
import it.polimi.blackjackbe.model.Mano;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.TavoloRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.ManoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManoServiceImplementation implements ManoService {

    private final ManoRepository manoRepository;

    @Override
    public List<GetAllManiResponse> getAllMani(){
        //Prendo dal Db tutte le mani.
        List<Mano> mani = manoRepository.findAll();
        //Inizializzo la variabile di risposta
        List<GetAllManiResponse> response = new ArrayList<>();

        //Per ogni mano creo un oggetto di risposta e lo aggiungo alla lista di risposta.
        for(Mano mano : mani){
            response.add(new GetAllManiResponse(
                mano.getManoId(),
                mano.getTavolo().getTavoloId(),
                mano.getTavolo().getPlayer().getUsername(),
                mano.getDataMano(),
                mano.getImporto()
            ));
        }
        return response;
    }


}
