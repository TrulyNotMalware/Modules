package dev.notypie.infrastructure.application;

import dev.notypie.aggregate.slack.SlackEventResponse;
import dev.notypie.aggregate.slack.SlackRequestHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AssistantService {

    ResponseEntity<?> categorization(Map<String, List<String>> headers, Map<String, Object> payload);
}
