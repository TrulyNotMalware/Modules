package dev.notypie.infrastructure.impl.command.slack.dto;

import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Deprecated( forRemoval = true )
public class SlackEventResponse {

    private String contentType;
    private SlackEventContents eventContents;
}