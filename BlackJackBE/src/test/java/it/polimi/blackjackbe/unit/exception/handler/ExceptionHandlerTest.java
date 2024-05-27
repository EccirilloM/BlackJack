package it.polimi.blackjackbe.unit.exception.handler;

import it.polimi.blackjackbe.exception.*;
import it.polimi.blackjackbe.exception.handler.ExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerTest {

    @InjectMocks
    ExceptionHandler controller;

    @Test
    void handleBadRequestException() {

        assertAll(() -> controller.handleBadRequestException(new BadRequestException("test")));
    }

    @Test
    void handleForbiddenException() {

        assertAll(() -> controller.handleForbiddenException(new ForbiddenException("test")));
    }

    @Test
    void handleNotFoundException() {

        assertAll(() -> controller.handleNotFoundException(new NotFoundException("test")));
    }

    @Test
    void handleConflictException() {

        assertAll(() -> controller.handleConflictException(new ConflictException("test")));
    }

    @Test
    void handleInternalServerErrorException() {

        assertAll(() -> controller.handleInternalServerErrorException(new InternalServerErrorException("test")));
    }
}