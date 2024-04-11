package dev.notypie.service.command.slack;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Deprecated(forRemoval = true)
public interface SlackService {

    ResponseEntity<?> categorization(Map<String, List<String>> headers, Map<String, Object> payload);
}
