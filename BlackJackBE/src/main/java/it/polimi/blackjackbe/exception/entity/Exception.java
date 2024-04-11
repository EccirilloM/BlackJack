package it.polimi.blackjackbe.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Exception {

    private String message;
    private HttpStatus status;
}
