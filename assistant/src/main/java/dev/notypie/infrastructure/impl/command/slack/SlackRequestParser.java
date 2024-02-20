package dev.notypie.infrastructure.impl.command.slack;

import dev.notypie.infrastructure.impl.command.slack.dto.contexts.Contexts;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;

import java.util.Map;

public interface SlackRequestParser {

    SlackEvent<? extends Contexts> parseRequest(SlackRequestHeaders headers, Map<String, Object> payload);
}
