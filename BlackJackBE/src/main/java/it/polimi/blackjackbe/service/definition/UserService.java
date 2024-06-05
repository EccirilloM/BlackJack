package it.polimi.blackjackbe.service.definition;

import it.polimi.blackjackbe.dto.request.AdminAggiornaDatiUtenteRequest;
import it.polimi.blackjackbe.dto.request.AggiornaDatiRequest;
import it.polimi.blackjackbe.dto.request.RegistrazioneRequest;
import it.polimi.blackjackbe.dto.response.UserResponse;

import java.util.List;

/**
 * Interfaccia che contiene le firme dei metodi del service che gestisce le operazioni relative agli utenti.
 * Implementazione -> {@link it.polimi.blackjackbe.service.implementation.UserServiceImplementation}.
 */
public interface UserService {

    /**
     * Recupera i dati di un utente specifico in base al suo ID.
     * @param userId ID dell'utente di cui recuperare i dati.
     * @return Risposta contenente i dati dell'utente.
     */
    UserResponse getUserDataById(Long userId);

    /**
     * Elimina un utente specifico in base al suo ID.
     * @param userId ID dell'utente da eliminare.
     */
    void deleteUser(Long userId);

    /**
     * Recupera tutti gli utenti con un determinato ruolo.
     * @param ruolo Ruolo degli utenti da recuperare.
     * @return Lista di risposte contenenti tutti gli utenti con il ruolo specificato.
     */
    List<UserResponse> getAllByRuolo(String ruolo);

    /**
     * Ricarica il saldo di tutti gli utenti.
     */
    void ricaricaSaldo();

    /**
     * Recupera tutti gli utenti presenti nel sistema.
     * @return Lista di risposte contenenti tutti gli utenti.
     */
    List<UserResponse> getAll();

    /**
     * Aggiorna i dati di un utente specifico.
     * @param aggiornaRequest Richiesta contenente i nuovi dati dell'utente.
     * @param userId ID dell'utente da aggiornare.
     * @return Risposta contenente i dati aggiornati dell'utente.
     */
    UserResponse aggiornaDatiUtente(AggiornaDatiRequest aggiornaRequest, Long userId);

    /**
     * Aggiorna i dati di un utente specifico da parte di un amministratore.
     * @param aggiornaRequest Richiesta contenente i nuovi dati dell'utente.
     * @param userId ID dell'utente da aggiornare.
     * @return Risposta contenente i dati aggiornati dell'utente.
     */
    UserResponse adminAggiornaDatiUtente(AdminAggiornaDatiUtenteRequest aggiornaRequest, Long userId);

    /**
     * Crea un nuovo economo.
     * @param request Richiesta di registrazione contenente i dettagli dell'economo da creare.
     */
    void creaEconomo(RegistrazioneRequest request);
}
