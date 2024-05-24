package it.polimi.blackjackbe.command;

import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;

import java.util.Map;

public abstract class Command {
    public abstract TavoloStatusResponse execute(Long userId, Map<String, Object> data);
}
