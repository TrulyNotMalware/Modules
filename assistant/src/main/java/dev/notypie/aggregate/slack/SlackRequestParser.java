package dev.notypie.aggregate.slack;

import dev.notypie.aggregate.slack.dto.contexts.Contexts;
import dev.notypie.aggregate.slack.event.SlackEvent;

import java.util.Map;

public interface SlackRequestParser {

    SlackEvent<? extends Contexts> parseRequest(SlackRequestHeaders headers, Map<String, Object> payload);
}
