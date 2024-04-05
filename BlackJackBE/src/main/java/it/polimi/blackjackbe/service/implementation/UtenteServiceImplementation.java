package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.request.LoginRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.Utente;
import it.polimi.blackjackbe.repository.UtenteRepository;
import it.polimi.blackjackbe.service.definition.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtenteServiceImplementation implements UtenteService {

    private final UtenteRepository utenteRepository;


    @Override
    public Utente login(LoginRequest loginRequest) {
        if(loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null || loginRequest.getUsername().isEmpty() || loginRequest.getPassword().isEmpty()){
            return null;
        }
        Optional<Utente> utente = utenteRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if(utente.isPresent()){
            return utente.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean registrazionePlayer(RegistrazioneRequest registrazioneRequest) {
        //TODO: Implementare più controlli
        if(registrazioneRequest == null || registrazioneRequest.getUsername() == null || registrazioneRequest.getPassword() == null || registrazioneRequest.getUsername().isEmpty() || registrazioneRequest.getPassword().isEmpty()){
            return false;
        }
        Utente u = new Utente();
        u.setUsername(registrazioneRequest.getUsername());
        u.setPassword(registrazioneRequest.getPassword());
        u.setRuolo(Ruolo.PLAYER);
        u.setDataNascita(registrazioneRequest.getDataNascita());
        u.setNome(registrazioneRequest.getNome());
        u.setCognome(registrazioneRequest.getCognome());
        u.setEmail(registrazioneRequest.getEmail());
        u.setDataRegistrazione(LocalDateTime.now());
        u.setSaldo(100.00);
        try{
            u = utenteRepository.save(u);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
