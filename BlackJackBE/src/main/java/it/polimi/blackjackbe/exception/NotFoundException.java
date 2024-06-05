package it.polimi.blackjackbe.exception;

/**
 * Classe per la gestione delle eccezioni con status code 403.
 * Estende {@link RuntimeException}, superclasse delle eccezioni
 * che possono essere lanciate durante il l'esecuzione della JVM.
 */
public class NotFoundException extends RuntimeException{
public NotFoundException(String message) {
        super(message);
    }
}
