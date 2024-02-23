package dev.notypie.application.command;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CommandService {

    ResponseEntity<?> executeCommand(Map<String, List<String>> headers, Map<String, Object> payload);
}