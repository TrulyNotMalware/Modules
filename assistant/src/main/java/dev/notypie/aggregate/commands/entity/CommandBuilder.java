package dev.notypie.aggregate.commands.entity;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Deprecated( forRemoval = true )

public interface CommandBuilder {

    boolean isSupport(String appType);
    Command buildCommand(String appId, Map<String, List<String>> headers, Map<String, Object> payload);
    ResponseEntity<?> executeCommand(Command command);
}
