package dev.notypie.service.command.slack;

import dev.notypie.infrastructure.impl.command.slack.dto.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;

import java.util.List;
import java.util.Map;

public interface SlackRequestParser {

    SlackEvent<SlackContext> parseSlackEventFromRequest(Map<String, List<String>> headers, Map<String, Object> payload);
}
