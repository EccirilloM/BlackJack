package it.polimi.blackjackbe.service.implementation;

import it.polimi.blackjackbe.builder.UserBuilder;
import it.polimi.blackjackbe.dto.request.AdminAggiornaDatiUtenteRequest;
import it.polimi.blackjackbe.dto.request.AggiornaDatiRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.UserResponse;
import it.polimi.blackjackbe.exception.BadRequestException;
import it.polimi.blackjackbe.exception.ConflictException;
import it.polimi.blackjackbe.exception.InternalServerErrorException;
import it.polimi.blackjackbe.exception.NotFoundException;
import it.polimi.blackjackbe.model.*;
import it.polimi.blackjackbe.repository.ManoRepository;
import it.polimi.blackjackbe.repository.TabacchiRepository;
import it.polimi.blackjackbe.repository.TavoloRepository;
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
/**
 * Implementazione del service che gestisce le operazioni relative agli utenti.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TabacchiRepository tabacchiRepository;
    private final TavoloRepository tavoloRepository;
    private final ManoRepository manoRepository;

    /**
     * Recupera i dati di un utente dato il suo ID.
     * @param userId ID dell'utente di cui recuperare i dati.
     * @return Una risposta contenente i dati dell'utente.
     */
    @Override
    public UserResponse getUserDataById(Long userId) {
        // Controlla se l'ID è valido.
        if(userId < 1){
            throw new BadRequestException("Id non Valido");
        }

        // Recupera l'utente dal repository.
        Optional<User> userExists = userRepository.findByUserId(userId);

        // Se l'utente non viene trovato, lancia un'eccezione.
        if(userExists.isEmpty()){
            throw new NotFoundException("Utente non trovato");
        }

        // Restituisce una risposta con i dati dell'utente.
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

    /**
     * Elimina un utente dato il suo ID.
     * @param userId ID dell'utente da eliminare.
     */
    @Override
    public void deleteUser(Long userId) {
        // L'ID autoincrement parte da 1.
        if(userId < 1) {
            throw new BadRequestException("Id non valido");
        }

        // Recupera l'utente dal repository.
        Optional<User> userExists = userRepository.findByUserId(userId);

        // Se l'utente non viene trovato, lancia un'eccezione.
        if(userExists.isEmpty()) {
            throw new NotFoundException("Utente non trovato");
        }

        if(userExists.get().getRuolo().equals(Ruolo.ADMIN)) {
            throw new ConflictException("Non è possibile eliminare un amministratore");
        }

        // Controlla se l'utente è associato a un tabacchi.
        List<Tabacchi> listaTabacchi = tabacchiRepository.findAll();
        for (Tabacchi tabacchi : listaTabacchi) {
            if (tabacchi.getEconomo().getUserId() == userId) {
                throw new ConflictException("L'utente è associato a un tabacchi. Elimina prima il tabacchi.");
            }
        }

        // Controlla se l'utente è associato a uno o più tavoli.
        List<Tavolo> listaTavoli = tavoloRepository.findAllByPlayer(userExists.get());
        if (listaTavoli != null && !listaTavoli.isEmpty()) {
            for (Tavolo tavolo : listaTavoli) {
                // Per ogni tavolo associato all'utente, elimina le mani del tavolo
                for (Mano mani : tavolo.getMani()) {
                    manoRepository.delete(mani);
                }
                // Elimina il tavolo
                tavoloRepository.delete(tavolo);
            }
        }

        // Elimina l'utente dal database.
        userRepository.delete(userExists.get());

        // Controlla se l'utente è stato eliminato correttamente.
        Optional<User> userDeleted = userRepository.findByUserId(userId);

        // Se l'utente non è stato eliminato, lancia un'eccezione.
        if (userDeleted.isPresent()) {
            throw new InternalServerErrorException("Errore nell'eliminazione dell'utente");
        }
    }

    /**
     * Recupera tutti gli utenti di un determinato ruolo.
     * @param ruolo Ruolo degli utenti da recuperare.
     * @return Una lista di risposte contenenti i dati degli utenti con il ruolo specificato.
     */
    @Override
    public List<UserResponse> getAllByRuolo(String ruolo) {
        // Controlla la validità del campo.
        if(ruolo.isBlank() || ruolo.isEmpty()) {
            throw new BadRequestException("Ruolo non valido");
        }

        // In base al ruolo richiesto dal client, crea una variabile da usare per chiamare il db.
        final Ruolo ruoloDaCercare = switch (ruolo) {
            case "PLAYER" -> Ruolo.PLAYER;
            case "ADMIN" -> Ruolo.ADMIN;
            case "ECONOMO" -> Ruolo.ECONOMO;
            default -> throw new BadRequestException("Ruolo non valido");
        };

        // Recupera dal database tutti gli utenti con il ruolo specificato.
        List<User> users = userRepository.findAllByRuolo(ruoloDaCercare);

        // Se non è presente nessun utente, lancia un'eccezione.
        if(users.isEmpty()) {
            throw new NotFoundException("Utenti non trovati");
        }

        // Inizializza la variabile di risposta.
        List<UserResponse> response = new ArrayList<>();

        // Per ogni utente, aggiunge all'array di risposta i dati.
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

        // Restituisce la lista di risposte.
        return response;
    }

    /**
     * Ricarica il saldo di tutti gli utenti con il ruolo PLAYER.
     */
    @Override
    public void ricaricaSaldo() {
        // Prendo dal db tutti gli utenti PLAYER.
        List<User> users = userRepository.findAllByRuolo(Ruolo.PLAYER);

        // Per ogni utente, ricarico il saldo.
        for (User user : users) {
            System.out.println("Ricarica saldo per l'utente: " + user.getUsername());
            System.out.println("Saldo corrente: " + user.getSaldo() + "€");
            // Aggiungi 50 al saldo corrente.
            user.setSaldo(user.getSaldo() + 50.0);
            userRepository.save(user);
            System.out.println("Saldo aggiornato: " + user.getSaldo() + "€");
        }
    }

    /**
     * Recupera tutti gli utenti.
     * @return Una lista di risposte contenenti i dati di tutti gli utenti.
     */
    @Override
    public List<UserResponse> getAll() {
        // Prendo dal db tutti gli utenti.
        List<User> users = userRepository.findAll();

        // Se non è presente nessun utente, lancio un'eccezione.
        if (users.isEmpty()) {
            throw new NotFoundException("Utenti non trovati");
        }

        // Inizializzo la variabile di risposta.
        List<UserResponse> response = new ArrayList<>();

        // Per ogni utente, aggiungo all'array di risposta i dati.
        for (User user : users) {
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

    /**
     * Aggiorna i dati di un utente.
     * @param aggiornaRequest Dati da aggiornare.
     * @param userId ID dell'utente da aggiornare.
     * @return Una risposta contenente i dati aggiornati dell'utente.
     */
    @Override
    public UserResponse aggiornaDatiUtente(AggiornaDatiRequest aggiornaRequest, Long userId) {
        // Prendo l'utente dal db con quell'id.
        Optional<User> userExists = userRepository.findByUserId(userId);

        // Se non esiste un utente con quell'id, lancio un'eccezione.
        if (userExists.isEmpty()) {
            throw new NotFoundException("Utente non trovato");
        }

        // Aggiorna il nome dell'utente se fornito.
        if (!aggiornaRequest.getNome().isBlank() && !aggiornaRequest.getNome().isEmpty()) {
            userExists.get().setNome(aggiornaRequest.getNome());
        }

        // Aggiorna il cognome dell'utente se fornito.
        if (!aggiornaRequest.getCognome().isBlank() && !aggiornaRequest.getCognome().isEmpty()) {
            userExists.get().setCognome(aggiornaRequest.getCognome());
        }

        // Aggiorna l'email dell'utente se fornita.
        if (!aggiornaRequest.getEmail().isBlank() && !aggiornaRequest.getEmail().isEmpty()) {
            userExists.get().setEmail(aggiornaRequest.getEmail());
        }

        // Aggiorna lo username dell'utente se fornito e non già in uso.
        if (!aggiornaRequest.getUsername().isBlank() && !aggiornaRequest.getUsername().isEmpty() &&
                !aggiornaRequest.getUsername().equals(userExists.get().getUsername())) {
            Optional<User> userExistsByUsername = userRepository.findByUsername(aggiornaRequest.getUsername());
            if (userExistsByUsername.isPresent()) {
                throw new BadRequestException("Username già in uso");
            }
            userExists.get().setUsername(aggiornaRequest.getUsername());
        }

        // Aggiorna la password dell'utente se fornita e corretta.
        if (!aggiornaRequest.getVecchiaPassword().isBlank() && !aggiornaRequest.getVecchiaPassword().isEmpty()
                && !aggiornaRequest.getNuovaPassword().isBlank() && !aggiornaRequest.getNuovaPassword().isEmpty()) {
            // Controlla se la vecchia password è corretta.
            if (!passwordEncoder.matches(aggiornaRequest.getVecchiaPassword(), userExists.get().getPassword())) {
                throw new BadRequestException("Vecchia Password non corretta");
            }

            // Controlla se la nuova password è uguale alla vecchia.
            if (aggiornaRequest.getVecchiaPassword().equals(aggiornaRequest.getNuovaPassword())) {
                throw new BadRequestException("La nuova password non può essere uguale alla vecchia");
            }

            // Setta la nuova password.
            userExists.get().setPassword(passwordEncoder.encode(aggiornaRequest.getNuovaPassword()));
        }

        // Salvo le modifiche nel db.
        userRepository.save(userExists.get());

        // Restituisce una risposta con i dati aggiornati dell'utente.
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

    /**
     * Metodo che permette a un amministratore di aggiornare i dati di un utente.
     * @param aggiornaRequest Dati aggiornati da applicare.
     * @param userId ID dell'utente da aggiornare.
     * @return Risposta contenente i dati aggiornati dell'utente.
     */
    @Override
    public UserResponse adminAggiornaDatiUtente(AdminAggiornaDatiUtenteRequest aggiornaRequest, Long userId) {
        // Prende l'utente dal database con l'ID specificato.
        Optional<User> userExists = userRepository.findByUserId(userId);

        // Se l'utente non esiste, lancia un'eccezione.
        if (userExists.isEmpty()) {
            throw new NotFoundException("Utente non trovato");
        }

        // Aggiorna il nome dell'utente se fornito.
        if (!aggiornaRequest.getNome().isBlank() && !aggiornaRequest.getNome().isEmpty()) {
            userExists.get().setNome(aggiornaRequest.getNome());
        }

        // Aggiorna il cognome dell'utente se fornito.
        if (!aggiornaRequest.getCognome().isBlank() && !aggiornaRequest.getCognome().isEmpty()) {
            userExists.get().setCognome(aggiornaRequest.getCognome());
        }

        // Aggiorna l'email dell'utente se fornita.
        if (!aggiornaRequest.getEmail().isBlank() && !aggiornaRequest.getEmail().isEmpty()) {
            userExists.get().setEmail(aggiornaRequest.getEmail());
        }

        // Aggiorna lo username dell'utente se fornito e non già in uso.
        if (!aggiornaRequest.getUsername().isBlank() && !aggiornaRequest.getUsername().isEmpty() &&
                !aggiornaRequest.getUsername().equals(userExists.get().getUsername())) {
            Optional<User> userExistsByUsername = userRepository.findByUsername(aggiornaRequest.getUsername());
            if (userExistsByUsername.isPresent()) {
                throw new BadRequestException("Username già in uso");
            }
            userExists.get().setUsername(aggiornaRequest.getUsername());
        }

        // Salva le modifiche nel database.
        userRepository.save(userExists.get());

        // Restituisce una risposta con i dati aggiornati dell'utente.
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

    /**
     * Metodo per creare un nuovo utente con il ruolo di Economo.
     * @param request Dati per la registrazione dell'utente.
     * @throws RuntimeException possibile eccezione causata da dati non validi.
     */
    @Override
    public void creaEconomo(RegistrazioneRequest request) {
        // Controlla se un utente con lo stesso username è già registrato.
        Optional<User> userAlreadyRegistered = userRepository.findByUsername(request.getUsername());

        // Se l'utente esiste già, lancia un'eccezione.
        if (userAlreadyRegistered.isPresent()) {
            throw new ConflictException("Username già registrato");
        }

        // Estrae e valida i dati dalla richiesta.
        final String nome = request.getNome().trim();
        final String cognome = request.getCognome().trim();
        final String username = request.getUsername().trim().toLowerCase();
        final String email = request.getEmail().trim().toLowerCase();
        final String password = request.getPassword();
        final LocalDateTime dataNascita = request.getDataNascita();
        final Ruolo ruolo = Ruolo.ECONOMO;

        checkUserData(List.of(nome, cognome, username, email, password));

        // Costruisce l'oggetto utente.
        User user = new UserBuilder()
                .nome(nome)
                .cognome(cognome)
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .ruolo(ruolo)
                .dataNascita(dataNascita)
                .dataRegistrazione(LocalDateTime.now())
                .saldo(100.00)
                .build();

        // Salva l'utente nel database.
        userRepository.save(user);

        // Verifica che l'utente sia stato correttamente registrato.
        Optional<User> userRegistered = userRepository.findByUsername(username);

        // Se l'utente non è stato trovato, lancia un'eccezione.
        if (userRegistered.isEmpty()) {
            throw new InternalServerErrorException("Errore durante la registrazione");
        }
    }

    /**
     * Metodo di utilità per controllare che i dati dell'utente non siano mancanti.
     * @param dataList Lista di dati da controllare.
     * @throws RuntimeException se uno dei dati è vuoto o nullo.
     */
    private void checkUserData(@NonNull List<String> dataList) throws RuntimeException {
        for (String data : dataList) {
            if (data.isEmpty() || data.isBlank()) {
                throw new BadRequestException("Dati mancanti");
            }
        }
    }
}
