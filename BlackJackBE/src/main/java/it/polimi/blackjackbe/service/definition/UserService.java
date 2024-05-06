package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.AdminAggiornaDatiUtenteRequest;
import it.polimi.blackjackbe.dto.request.AggiornaDatiRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getUserDataById(Long userId);

    void deleteUser(Long userId);

    List<UserResponse> getAllByRuolo(String ruolo);

    void ricaricaSaldo();

    List<UserResponse> getAll();

    UserResponse aggiornaDatiUtente(AggiornaDatiRequest aggiornaRequest, Long userId);

    UserResponse adminAggiornaDatiUtente(AdminAggiornaDatiUtenteRequest aggiornaRequest, Long userId);

    void creaEconomo(RegistrazioneRequest request);
}
