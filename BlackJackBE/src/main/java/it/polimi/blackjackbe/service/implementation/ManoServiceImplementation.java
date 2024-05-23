package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.response.GetAllManiResponse;
import it.polimi.blackjackbe.model.Mano;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.TavoloRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.ManoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManoServiceImplementation implements ManoService {

    private final UserRepository userRepository;
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

    @Override
    public List<GetAllManiResponse> getAllManiByUserId(Long userId) {

        if(userId < 0L){
            throw new IllegalArgumentException("userId non può essere null o negativo");
        }

        //Prendo dal Db l'utente con l'id passato come parametro.
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()){
            throw new IllegalArgumentException("Utente non trovato");
        }

        //Prendo dal Db tutte le mani.
        List<Mano> mani = manoRepository.findAll();
        //Inizializzo la variabile di risposta
        List<GetAllManiResponse> response = new ArrayList<>();

        //Per ogni mano creo un oggetto di risposta e lo aggiungo alla lista di risposta.
        for(Mano mano : mani){
            if(mano.getTavolo().getPlayer().getUserId()== userId){
                response.add(new GetAllManiResponse(
                    mano.getManoId(),
                    mano.getTavolo().getTavoloId(),
                    mano.getTavolo().getPlayer().getUsername(),
                    mano.getDataMano(),
                    mano.getImporto()
                ));
            }
        }
        return response;
    }


}
