package it.polimi.blackjackbe.exception;

public class InternalServerErrorException extends RuntimeException{

        public InternalServerErrorException(String message) {
            super(message);
        }
}
