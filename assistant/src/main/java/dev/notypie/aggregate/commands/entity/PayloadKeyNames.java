package dev.notypie.aggregate.commands.entity;

import dev.notypie.global.constants.Constants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Deprecated( forRemoval = true )
public enum PayloadKeyNames {
    API_APP_ID(Constants.SLACK_REQUEST_KEY_NAME);

    private final String keyName;
}
