package dev.notypie.controller;

import dev.notypie.application.OAuth2Service;
import dev.notypie.dto.RegisterOAuthClient;
import dev.notypie.dto.ResponseRegisteredClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/oauth2")
@RestController
public class OAuthController {
    private final OAuth2Service service;

    @PostMapping(value = "/client", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ResponseRegisteredClient> register(
            @RequestBody RegisterOAuthClient authClient)
    {
        return EntityModel.of(this.service.registerNewClient(authClient));
    }
}