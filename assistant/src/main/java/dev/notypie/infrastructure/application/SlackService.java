package dev.notypie.infrastructure.application;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SlackService {

    ResponseEntity<?> categorization(Map<String, List<String>> headers, Map<String, Object> payload);
}
