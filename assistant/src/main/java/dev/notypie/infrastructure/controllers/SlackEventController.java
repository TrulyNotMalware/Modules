package dev.notypie.infrastructure.controllers;


import dev.notypie.global.constants.Constants;
import dev.notypie.service.command.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Profile("slack")
@RestController
@RequestMapping("/api/slack")
@RequiredArgsConstructor
public class SlackEventController {

    @Qualifier(Constants.SLACK_COMMAND_SERVICE)
    private final CommandService service;

    //Api Challenge
    @PostMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> slackEventController(
            @RequestHeader MultiValueMap<String, String> headers,
            @RequestBody Map<String, Object> payload
    ){
        return this.service.executeCommand(headers, payload);
    }
}