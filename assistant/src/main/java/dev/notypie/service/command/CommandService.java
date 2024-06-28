package dev.notypie.service.command;

import dev.notypie.aggregate.commands.entity.Command;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Deprecated( forRemoval = true )
public interface CommandService {
    Command buildCommand(Map<String, List<String>> headers, Map<String, Object> payload);
    ResponseEntity<?> executeCommand(Command command);
    ResponseEntity<?> executeCommand(Map<String, List<String>> headers, Map<String, Object> payload);
}