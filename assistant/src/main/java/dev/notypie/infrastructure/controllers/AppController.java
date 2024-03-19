package dev.notypie.infrastructure.controllers;

import dev.notypie.aggregate.app.dto.AppRegisterDto;
import dev.notypie.application.app.AppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/app")
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void registerNewApp(
            @RequestBody AppRegisterDto appRegisterDto){
        this.appService.registerApp(appRegisterDto);
    }
}
