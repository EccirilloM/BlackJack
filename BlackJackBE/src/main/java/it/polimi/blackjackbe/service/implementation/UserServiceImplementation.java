package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.dto.request.AggiornaDatiRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.UserResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.ConflictException;
import it.polimi.blackjackbe.exception.InternalServerErrorException;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.Ruolo;
import it.polimi.blackjackbe.model.Tabacchi;
import it.polimi.blackjackbe.model.User;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.UserRepository;
import it.polimi.blackjackbe.service.definition.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TabacchiRepository tabacchiRepository;

    @Override
    public UserResponse getUserData(Long userId) {
        if(userId < 1){
            throw new BadRequestException("Id non Valido");
        }

        Optional<User> userExists = userRepository.findByUserId(userId);

        if(userExists.isEmpty()){
            throw new NotFoundException("Utente non trovato");
        }

        return new UserResponse(
                userExists.get().getUserId(),
                userExists.get().getNome(),
                userExists.get().getCognome(),
                userExists.get().getEmail(),
                userExists.get().getUsername(),
                userExists.get().getRuolo(),
                userExists.get().getDataNascita(),
                userExists.get().getDataRegistrazione(),
                userExists.get().getSaldo()
        );}

    @Override
    public void deleteUser(Long userId) {
        //L'id autoincrement parte da 1.
        if(userId < 1) {
            throw new BadRequestException("Id non valido");
        }

        //Prendo l'utente dal db con quell'id.
        Optional<User> userExists = userRepository.findByUserId(userId);

        //Se non esiste un utente con quell'id, lancio un'eccezione.
        if(userExists.isEmpty()) {
            throw new NotFoundException("Utente non trovato");
        }

        List<Tabacchi> listaTabacchi = tabacchiRepository.findAll();
        for(Tabacchi tabacchi : listaTabacchi){
            if(tabacchi.getEconomo().getUserId() == userId){
                throw new ConflictException("l'utente è associato ad un tabacchi.");
            }
        }

        //Elimino l'utente dal database.
        userRepository.delete(userExists.get());

        //Controllo se esiste ancora l'utente con quell'id
        Optional<User> userDeleted = userRepository.findByUserId(userId);

        //Se non è stato eliminato, lancio un'eccezione.
        if(userDeleted.isPresent()) {
            throw new InternalServerErrorException("Errore nell'eliminazione dell'utente");
        }
    }

    @Override
    public List<UserResponse> getAllByRuolo(String ruolo) {
        //Controllo la validità del campo.
        if(ruolo.isBlank() || ruolo.isEmpty()) {
            throw new BadRequestException("Ruolo non valido");
        }

        //In base al ruolo richiesto dal client, creo una variabile da usare per chiamare il db.
        final Ruolo ruoloDaCercare = switch (ruolo) {
            case "PLAYER" -> Ruolo.PLAYER;
            case "ADMIN" -> Ruolo.ADMIN;
            default -> throw new BadRequestException("Ruolo non valido");
        };

        //Prendo dal db tutti gli utenti.
        List<User> users = userRepository.findAllByRuolo(ruoloDaCercare);

        //Se non è presente nessun utente lancio un'eccezione.
        if(users.isEmpty()) {
            throw new NotFoundException("Utenti non trovati");
        }

        //Inizializzo la variabile di risposta.
        List<UserResponse> response = new ArrayList<>();

        //Per ogni utente, aggiungo all'array di risposta i dati.
        for(User user: users) {
            response.add(new UserResponse(
                    user.getUserId(),
                    user.getNome(),
                    user.getCognome(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getRuolo(),
                    user.getDataNascita(),
                    user.getDataRegistrazione(),
                    user.getSaldo()
            ));
        }

        return response;
    }

    @Override
    public void ricaricaSaldo() {
        //Prendo dal db tutti gli utenti PLAYER.
        List<User> users = userRepository.findAllByRuolo(Ruolo.PLAYER);

        //Se non è presente nessun utente lancio un'eccezione.
        if(users.isEmpty()) {
            throw new NotFoundException("Utenti non trovati");
        }

        //Per ogni utente, ricarico il saldo.
        for(User user: users) {
            System.out.println("Ricarica saldo per l'utente: " + user.getUsername());
            System.out.println("Saldo corrente: " + user.getSaldo() + "€");
            //Aggiungi 50 al saldo corrente
            user.setSaldo(user.getSaldo() + 50.0);
            userRepository.save(user);
            System.out.println("Saldo aggiornato: " + user.getSaldo() + "€");
        }

    }

    @Override
    public List<UserResponse> getAll() {
        //Prendo dal db tutti gli utenti.
        List<User> users = userRepository.findAll();

        //Se non è presente nessun utente lancio un'eccezione.
        if(users.isEmpty()) {
            throw new NotFoundException("Utenti non trovati");
        }

        //Inizializzo la variabile di risposta.
        List<UserResponse> response = new ArrayList<>();

        //Per ogni utente, aggiungo all'array di risposta i dati.
        for(User user: users) {
            response.add(new UserResponse(
                    user.getUserId(),
                    user.getNome(),
                    user.getCognome(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getRuolo(),
                    user.getDataNascita(),
                    user.getDataRegistrazione(),
                    user.getSaldo()
            ));
        }

        return response;
    }

    @Override
    public UserResponse aggiornaDatiUtente(AggiornaDatiRequest aggiornaRequest, Long userId) {
        //Prendo l'utente dal db con quell'id.
        Optional<User> userExists = userRepository.findByUserId(userId);

        //Se non esiste un utente con quell'id, lancio un'eccezione.
        if(userExists.isEmpty()) {
            throw new NotFoundException("Utente non trovato");
        }

        if(!aggiornaRequest.getNome().isBlank() && !aggiornaRequest.getNome().isEmpty()){
            userExists.get().setNome(aggiornaRequest.getNome());
        }

        if(!aggiornaRequest.getCognome().isBlank() && !aggiornaRequest.getCognome().isEmpty()){
            userExists.get().setCognome(aggiornaRequest.getCognome());
        }

        if(!aggiornaRequest.getEmail().isBlank() && !aggiornaRequest.getEmail().isEmpty()){
            userExists.get().setEmail(aggiornaRequest.getEmail());
        }

        if(!aggiornaRequest.getUsername().isBlank() && !aggiornaRequest.getUsername().isEmpty() &&
        !aggiornaRequest.getUsername().equals(userExists.get().getUsername())){
            Optional<User> userExistsByUsername = userRepository.findByUsername(aggiornaRequest.getUsername());
            if(userExistsByUsername.isPresent()){
                throw new BadRequestException("Username già in uso");
            }
            userExists.get().setUsername(aggiornaRequest.getUsername());
        }

        if(!aggiornaRequest.getVecchiaPassword().isBlank() && !aggiornaRequest.getVecchiaPassword().isEmpty()
        && !aggiornaRequest.getNuovaPassword().isBlank() && !aggiornaRequest.getNuovaPassword().isEmpty()){
            //Controlla se la vecchia password è corretta
            if(!passwordEncoder.matches(aggiornaRequest.getVecchiaPassword(), userExists.get().getPassword())){
                throw new BadRequestException("Vecchia Password non corretta");
            }

            //Controllo se la nuova password è uguale alla vecchia
            if(aggiornaRequest.getVecchiaPassword().equals(aggiornaRequest.getNuovaPassword())){
                throw new BadRequestException("La nuova password non può essere uguale alla vecchia");
            }

            //Setto la nuova password
            userExists.get().setPassword(passwordEncoder.encode(aggiornaRequest.getNuovaPassword()));
        }

        //Salvo le modifiche nel db.
        userRepository.save(userExists.get());

        return new UserResponse(
                userExists.get().getUserId(),
                userExists.get().getNome(),
                userExists.get().getCognome(),
                userExists.get().getEmail(),
                userExists.get().getUsername(),
                userExists.get().getRuolo(),
                userExists.get().getDataNascita(),
                userExists.get().getDataRegistrazione(),
                userExists.get().getSaldo()
        );
    }

    @Override
    public void creaEconomo(RegistrazioneRequest request){
        Optional<User> userAlreadyRegistered = userRepository.findByUsername(request.getUsername());

        if (userAlreadyRegistered.isPresent()) {
            throw new ConflictException("Username già registrato");
        }

        final String nome = request.getNome().trim();
        final String cognome = request.getCognome().trim();
        final String username = request.getUsername().trim().toLowerCase();
        final String email = request.getEmail().trim().toLowerCase();
        final String password = request.getPassword();
        final LocalDateTime dataNascita = request.getDataNascita();
        final Ruolo ruolo = Ruolo.ECONOMO;

        checkUserData(List.of(nome, cognome, username, email, password));

        User user = new User();
        user.setNome(nome);
        user.setCognome(cognome);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRuolo(ruolo);
        user.setDataNascita(dataNascita);
        user.setDataRegistrazione(LocalDateTime.now());
        user.setSaldo(100.00);

        userRepository.save(user);

        Optional<User> userRegistered = userRepository.findByUsername(username);

        if (userRegistered.isEmpty()) {
            throw new InternalServerErrorException("Errore durante la registrazione");
        }
    }

    private void checkUserData(@NonNull List<String> dataList) throws RuntimeException {
        for (String data : dataList) {
            if (data.isEmpty() || data.isBlank()) {
                throw new BadRequestException("Dati mancanti");
            }
        }
    }
}
