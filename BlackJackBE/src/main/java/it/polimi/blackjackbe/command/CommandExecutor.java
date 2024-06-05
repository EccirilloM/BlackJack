package it.polimi.blackjackbe.command;

import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Map;

/**
 * Classe che esegue i comandi nel contesto dell'applicazione.
 * Utilizza il contesto dell'applicazione per recuperare e eseguire i comandi specifici.
 */
@Component
@AllArgsConstructor
public class CommandExecutor {
    private final ApplicationContext applicationContext;

    /**
     * Esegue un comando specifico identificato dal suo nome.
     * @param command Nome del comando da eseguire.
     * @param userId ID dell'utente che richiede il comando.
     * @param data Dati aggiuntivi richiesti per l'esecuzione del comando.
     * @return La risposta con lo stato del tavolo dopo l'esecuzione del comando.
     */
    public TavoloStatusResponse executeCommand(String command, Long userId, Map<String, Object> data) {
        // Recupera il bean del comando specificato dal contesto dell'applicazione
        Command commandFound = applicationContext.getBean(command, Command.class);
        // Esegue il comando recuperato e ritorna la risposta con lo stato del tavolo
        return commandFound.execute(userId, data);
    }

    /**
     * Recupera i nomi di tutti i comandi disponibili nel contesto dell'applicazione.
     * @return Una collezione di nomi di comandi disponibili.
     */
    public Collection<String> getCommandNames() {
        // Recupera tutti i bean di tipo Command dal contesto dell'applicazione e ritorna i loro nomi
        return applicationContext.getBeansOfType(Command.class).keySet();
    }
}
