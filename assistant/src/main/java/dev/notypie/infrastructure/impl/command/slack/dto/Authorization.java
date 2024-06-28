package dev.notypie.infrastructure.impl.command.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@Deprecated( forRemoval = true )
public class Authorization {
    @JsonProperty("enterprise_id")
    private String enterpriseId;

    @JsonProperty("team_id")
    private String teamId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("is_bot")
    private boolean isBot;

    @JsonProperty("is_enterprise_install")
    private boolean isEnterpriseInstall;
}
