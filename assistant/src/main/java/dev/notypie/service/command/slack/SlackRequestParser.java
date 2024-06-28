package dev.notypie.service.command.slack;

import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.events.SlackEvent;

import java.util.List;
import java.util.Map;

@Deprecated( forRemoval = true )
public interface SlackRequestParser {

    SlackEvent<SlackContext> parseSlackEventFromRequest(Map<String, List<String>> headers, Map<String, Object> payload);
}
