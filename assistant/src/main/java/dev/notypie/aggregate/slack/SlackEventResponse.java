package dev.notypie.aggregate.slack;

import dev.notypie.aggregate.slack.dto.SlackEventContents;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlackEventResponse {

    private String contentType;
    private SlackEventContents eventContents;
}