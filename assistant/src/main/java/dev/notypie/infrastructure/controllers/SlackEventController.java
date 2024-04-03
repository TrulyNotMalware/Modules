package dev.notypie.infrastructure.controllers;


import dev.notypie.aggregate.commands.entity.Command;
import dev.notypie.service.command.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final CommandService service;

    //Api Challenge
    @PostMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> slackEventController(
            @RequestHeader MultiValueMap<String, String> headers,
            @RequestBody Map<String, Object> payload
    ){
        Command slackCommand = this.service.buildCommand(headers, payload);
        return this.service.executeCommand(slackCommand);
    }
}