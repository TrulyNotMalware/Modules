package dev.notypie.infrastructure.application;

import dev.notypie.aggregate.commands.entity.Command;
import dev.notypie.aggregate.commands.entity.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandHandlerImpl implements CommandHandler {

    private final SlackCommandHandler slackCommandHandler;

    @Override
    public Command parseCommand(Map<String, List<String>> headers, Map<String, Object> payload) {
        return null;
    }

    @Override
    public void executeCommand() {

    }
}
