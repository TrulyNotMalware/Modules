package dev.notypie.infrastructure.service.command;

import dev.notypie.infrastructure.impl.command.slack.dto.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;

import java.util.List;
import java.util.Map;

public interface SlackRequestParser {

    SlackEvent<? extends SlackContext> parseRequest(Map<String, List<String>> headers, Map<String, Object> payload);
}
