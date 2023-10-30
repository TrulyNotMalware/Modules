package dev.notypie.controllers;

import dev.notypie.application.RefreshTokenService;
import dev.notypie.application.UserCRUDService;
import dev.notypie.dto.TokenResponseDto;
import dev.notypie.dto.UserRegisterDto;
import dev.notypie.jwt.dto.JwtDto;
import dev.notypie.jwt.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final UserCRUDService service;
    private final RefreshTokenService refreshTokenService;

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

    //10.30 Add Reissue accessToken.
    @GetMapping(value = "/reissue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseDto> reissueAccessToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @CookieValue("refresh-token") String refreshToken
    ){
        accessToken = accessToken.replace("Bearer ","");
        //Refresh Token.
        JwtDto reissueToken = this.refreshTokenService.refreshJwtToken(accessToken, refreshToken);
        ResponseCookie responseCookie = this.refreshTokenService.createRefreshToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(TokenResponseDto.toTokenResponseDto(reissueToken));
    }
}
