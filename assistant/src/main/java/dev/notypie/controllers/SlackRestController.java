package dev.notypie.controllers;

import dev.notypie.aggregate.slack.dto.SlackChallengeContext;
import dev.notypie.application.AssistantService;
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
public class SlackRestController {

    private final AssistantService service;

    //Api Challenge
    @PostMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> slackEventController(
            @RequestHeader MultiValueMap<String, String> headers,
            @RequestBody Map<String, Object> data
    ){
        return this.service.categorization(headers, data);
//        return new ResponseEntity<>(this.service, HttpStatus.OK);
    }
}