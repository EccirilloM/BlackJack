package it.polimi.blackjackbe.exception.handler;

import it.polimi.blackjackbe.exception.*;
import it.polimi.blackjackbe.exception.entity.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Gestore globale delle eccezioni per l'applicazione.
 * Questa classe cattura diverse eccezioni e restituisce risposte HTTP appropriate.
 */
@ControllerAdvice
public class ExceptionHandler {

    /**
     * Handler delle eccezioni con status 400: il server non è in grado di processare la richiesta del client.
     * @param e Eccezione di tipo BadRequestException.
     * @return Risposta di errore al client con messaggio e status.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Exception> handleBadRequestException(BadRequestException e) {
        // Crea una nuova eccezione personalizzata con il messaggio dell'eccezione originale e lo stato HTTP 400
        Exception customException = new Exception(e.getMessage(), HttpStatus.BAD_REQUEST);

        // Restituisce una risposta HTTP con l'eccezione personalizzata e lo stato HTTP 400
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler delle eccezioni con status 403: il client non ha i permessi adatti.
     * @param e Eccezione di tipo ForbiddenException.
     * @return Risposta di errore al client con messaggio e status.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ForbiddenException.class)
    public ResponseEntity<Exception> handleForbiddenException(ForbiddenException e) {
        // Crea una nuova eccezione personalizzata con il messaggio dell'eccezione originale e lo stato HTTP 403
        Exception customException = new Exception(e.getMessage(), HttpStatus.FORBIDDEN);

        // Restituisce una risposta HTTP con l'eccezione personalizzata e lo stato HTTP 403
        return new ResponseEntity<>(customException, HttpStatus.FORBIDDEN);
    }

    /**
     * Handler delle eccezioni con status 404: il server non è in grado di trovare la risorsa richiesta.
     * @param e Eccezione di tipo NotFoundException.
     * @return Risposta di errore al client con messaggio e status.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Exception> handleNotFoundException(NotFoundException e) {
        // Crea una nuova eccezione personalizzata con il messaggio dell'eccezione originale e lo stato HTTP 404
        Exception customException = new Exception(e.getMessage(), HttpStatus.NOT_FOUND);

        // Restituisce una risposta HTTP con l'eccezione personalizzata e lo stato HTTP 404
        return new ResponseEntity<>(customException, HttpStatus.NOT_FOUND);
    }

    /**
     * Handler delle eccezioni con status 409: la richiesta è in conflitto con lo stato attuale del server.
     * @param e Eccezione di tipo ConflictException.
     * @return Risposta di errore al client con messaggio e status.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<Exception> handleConflictException(ConflictException e) {
        // Crea una nuova eccezione personalizzata con il messaggio dell'eccezione originale e lo stato HTTP 409
        Exception customException = new Exception(e.getMessage(), HttpStatus.CONFLICT);

        // Restituisce una risposta HTTP con l'eccezione personalizzata e lo stato HTTP 409
        return new ResponseEntity<>(customException, HttpStatus.CONFLICT);
    }

    /**
     * Handler delle eccezioni con status 500: il server non è in grado di gestire la richiesta.
     * @param e Eccezione di tipo InternalServerErrorException.
     * @return Risposta di errore al client con messaggio e status.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = InternalServerErrorException.class)
    public ResponseEntity<Exception> handleInternalServerErrorException(InternalServerErrorException e) {
        // Crea una nuova eccezione personalizzata con il messaggio dell'eccezione originale e lo stato HTTP 500
        Exception customException = new Exception(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        // Restituisce una risposta HTTP con l'eccezione personalizzata e lo stato HTTP 500
        return new ResponseEntity<>(customException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handler delle eccezioni per il validatore dei campi dei DTO.
     * @param e Eccezione di tipo MethodArgumentNotValidException.
     * @return Risposta di errore al client con messaggio e status.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Exception> handleMethodArgumentNotValidException(org.springframework.web.bind.MethodArgumentNotValidException e) {
        // Estrae il messaggio di errore dal risultato del binding dell'eccezione
        String message = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        // Crea una nuova eccezione personalizzata con il messaggio di errore e lo stato HTTP 400
        Exception customException = new Exception(message, HttpStatus.BAD_REQUEST);

        // Restituisce una risposta HTTP con l'eccezione personalizzata e lo stato HTTP 400
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler generico per tutte le altre eccezioni.
     * @param e Eccezione generica.
     * @return Risposta di errore al client con messaggio e status.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = java.lang.Exception.class)
    public ResponseEntity<java.lang.Exception> exception(java.lang.Exception e) {
        // Stampa lo stack trace dell'eccezione per il debug
        e.printStackTrace();

        // Restituisce una risposta HTTP con l'eccezione originale e lo stato HTTP 500
        return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
