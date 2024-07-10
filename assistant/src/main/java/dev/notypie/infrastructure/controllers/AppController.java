package dev.notypie.infrastructure.controllers;

import dev.notypie.domain.app.dto.AppRegisterDto;
import dev.notypie.domain.app.dto.AppResponseDto;
import dev.notypie.domain.app.dto.EnableAppDto;
import dev.notypie.service.app.AppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/app")
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponseDto> registerNewApp(
            @RequestBody AppRegisterDto appRegisterDto){
        this.appService.registerApp(appRegisterDto);
        return null;
    }

    @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void enableApplication(
            @RequestBody EnableAppDto enableAppDto
    ){
        this.appService.enableApplication(enableAppDto);
    }
}
