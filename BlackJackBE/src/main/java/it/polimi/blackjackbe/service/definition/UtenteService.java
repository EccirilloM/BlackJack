package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.LoginRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.model.Utente;

public interface UtenteService {
    public Utente login(LoginRequest loginRequest);
    public boolean registrazionePlayer(RegistrazioneRequest registrazioneRequest);
}
