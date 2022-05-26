package com.banking.account.cmd.infrastructure;

import com.banking.cqrs.core.commands.BaseCommand;
import com.banking.cqrs.core.commands.CommandHandlerMethod;
import com.banking.cqrs.core.infrastructure.CommandDispacher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//Mediator Implement
@Service
public class AccountCommandDispatcher implements CommandDispacher {
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        List<CommandHandlerMethod> handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
       var handlers =  routes.get(command.getClass());
       if(handlers == null || handlers.size() != 1)
           throw new RuntimeException("handler no apropiado");
       handlers.get(0).handle(command);
    }
}
