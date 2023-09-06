package dev.notypie.controllers;

import dev.notypie.application.UserCRUDService;
import dev.notypie.dto.UserRegisterDto;
import dev.notypie.jwt.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final UserCRUDService service;

    @PostMapping(value = "/register", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<UserDto> register(
            @RequestBody @Valid UserRegisterDto userRegisterDto
            ){
        UserDto userResponseDto = this.service.register(userRegisterDto);
        Links allLinks;
        Link selfLink = linkTo(methodOn(AuthenticationController.class).register(userRegisterDto)).withSelfRel();
        allLinks = Links.of(selfLink);

        return EntityModel.of(userResponseDto, allLinks);
    }
}
