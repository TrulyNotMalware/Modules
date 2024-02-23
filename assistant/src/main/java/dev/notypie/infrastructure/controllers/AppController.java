package dev.notypie.infrastructure.controllers;

import dev.notypie.application.app.AppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/command")
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;

}
