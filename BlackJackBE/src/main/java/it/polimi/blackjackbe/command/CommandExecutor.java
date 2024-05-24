package it.polimi.blackjackbe.command;

import it.polimi.blackjackbe.dto.response.TavoloStatusResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@AllArgsConstructor
public class CommandExecutor {
    private final ApplicationContext applicationContext;

    public TavoloStatusResponse executeCommand(String command, Long userId, Map<String, Object> data) {
        Command commandFound = applicationContext.getBean(command, Command.class);
        return commandFound.execute(userId, data);
    }

    public Collection<String> getCommandNames() {
        return applicationContext.getBeansOfType(Command.class).keySet();
    }
}
