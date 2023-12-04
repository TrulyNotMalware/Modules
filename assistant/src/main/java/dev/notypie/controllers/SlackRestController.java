package dev.notypie.controllers;

import dev.notypie.aggregate.slack.dto.SlackChallengeContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Profile("slack")
@RestController
@RequestMapping("/api/slack")
@RequiredArgsConstructor
public class SlackRestController {

    //Api Challenge
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SlackChallengeContext> slackEventController(
            @RequestBody Map<String, Object> data
    ){
        return new ResponseEntity<>(challengeRequest, HttpStatus.OK);
    }
}