package dev.notypie.application;

import dev.notypie.dto.RegisterOAuthClient;
import dev.notypie.dto.ResponseRegisteredClient;

public interface OAuth2Service {
    ResponseRegisteredClient registerNewClient(RegisterOAuthClient oAuthClient);
}
