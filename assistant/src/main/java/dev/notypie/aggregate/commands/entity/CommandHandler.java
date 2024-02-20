package dev.notypie.aggregate.commands.entity;

import java.util.List;
import java.util.Map;

public interface CommandHandler {

    Command parseCommand(Map<String, List<String>> headers, Map<String, Object> payload);

    void executeCommand();

}
