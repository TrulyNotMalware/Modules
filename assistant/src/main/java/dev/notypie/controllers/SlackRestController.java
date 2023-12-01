package dev.notypie.controllers;

import dev.notypie.aggregate.slack.dto.SlackChallengeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Profile("slack-bolt")
@RestController
@RequestMapping("/api/slack")
@RequiredArgsConstructor
public class SlackRestController {

    //Api Challenge
    @PostMapping(value = "/challenge", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SlackChallengeRequest> slackEventController(
            @Valid @RequestBody SlackChallengeRequest challengeRequest
    ){
        return new ResponseEntity<>(challengeRequest, HttpStatus.OK);
    }
}