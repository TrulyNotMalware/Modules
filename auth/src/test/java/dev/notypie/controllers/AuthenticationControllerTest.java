package dev.notypie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.application.RefreshTokenService;
import dev.notypie.application.UserCRUDService;
import dev.notypie.base.ControllerTest;
import dev.notypie.builders.UserRegisterDtoBuilder;
import dev.notypie.configurations.SecurityConfiguration;
import dev.notypie.domain.Users;
import dev.notypie.dto.UserRegisterDto;
import dev.notypie.exchanger.UserInfoExchanger;
import dev.notypie.jwt.dto.JwtDto;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@Tag("auth")
@WebMvcTest(controllers = AuthenticationController.class)
@Import(SecurityConfiguration.class) //WebSecurityCustomizer import.
public class AuthenticationControllerTest extends ControllerTest {

    private static final String accessToken = "test-access-token";
    private static final String refreshToken = "test-refresh-token";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserCRUDService userService;

    //10.31 Add Test case
    @MockBean
    RefreshTokenService refreshTokenService;

    private UserRegisterDtoBuilder dtoBuilder;
    private Users user;
    private UserRegisterDto register;
    private JwtDto jwtDto;

    //Initialize
    @BeforeEach
    void setUp(){
        //Create Default Users for test
        this.dtoBuilder = new UserRegisterDtoBuilder();
        this.register = this.dtoBuilder.build();
        this.user = UserInfoExchanger.exchangeToUsers(this.register);

        this.jwtDto = JwtDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .accessTokenExpiredDate(new Date())
                .build();
        ResponseCookie responseCookie = ResponseCookie.from("refresh-token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Long.parseLong("20000")).build();
        //register default users.
        Mockito.when(this.userService.register(any(UserRegisterDto.class))).thenReturn(UserInfoExchanger.exchangeToUserDto(this.user));
        Mockito.when(this.refreshTokenService.refreshJwtToken(any(String.class), any(String.class))).thenReturn(this.jwtDto);
        Mockito.when(this.refreshTokenService.createRefreshToken(any(String.class))).thenReturn(responseCookie);
    }
    @Test
    @DisplayName("[app.Auth]Failed when incorrect user registered.")
    void failedWhenIncorrectInput() throws Exception{
        //given
        List<UserRegisterDto> incorrectUsers = new ArrayList<>();
        //Create Incorrect input users.
        String userName = this.dtoBuilder.userName;
        this.dtoBuilder.userName = "#%*@%#(*@$!&$#%#";
        incorrectUsers.add(this.dtoBuilder.build());

        String userId = dtoBuilder.userId;
        this.dtoBuilder.userName = userName;
        this.dtoBuilder.userId = "1#%%#%%#%*!$";
        incorrectUsers.add(this.dtoBuilder.build());

        String email = this.dtoBuilder.email;
        this.dtoBuilder.userId = userId;
        this.dtoBuilder.email = "!_!)!@test.mail";
        incorrectUsers.add(this.dtoBuilder.build());

        this.dtoBuilder.email = email;
        this.dtoBuilder.password = "SELECT * FROM USERS";
        incorrectUsers.add(this.dtoBuilder.build());

        //when & then
        for(UserRegisterDto incorrectUser : incorrectUsers){
            this.mockMvc.perform(
                    post("/api/auth/register")
                            .with(csrf())
                            .accept(MediaTypes.HAL_JSON_VALUE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(this.objectMapper.writeValueAsBytes(incorrectUser))
            ).andExpect(status().isBadRequest());
        }
    }

    @Test
    @DisplayName("[app.Auth]Success when correct user registered.")
    void successWhenCorrectInput() throws Exception{
        //given
        //when
        ResultActions results = this.mockMvc.perform(
                post("/api/auth/register")
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsBytes(this.register))
        );
        //then
        results.andExpectAll(status().isOk(),
                jsonPath("_links").isNotEmpty(),
                jsonPath("userId").value(this.register.getUserId()),
                jsonPath("email").value(this.register.getEmail()));
    }

    @Test
    @DisplayName("[app.Auth]Reissue endpoint works successfully")
    void successReissueEndpoint() throws Exception{
        //given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken);
        Cookie cookie = new Cookie("refresh-token",refreshToken);
        //when
        ResultActions results = this.mockMvc.perform(
                get("/api/auth/reissue")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .cookie(cookie)
        );
        //then
        results.andExpectAll(status().isOk(),
                jsonPath("accessToken").value(accessToken),
                jsonPath("expired").isNotEmpty());
    }

    @Test
    @DisplayName("[app.Auth]Failed reissue access token")
    void failedReissueEndpoint() throws Exception{
        //given
        List<HttpHeaders> inCorrectHeaders = new ArrayList<>();
        List<Cookie> inCorrectCookies = new ArrayList<>();
        HttpHeaders emptyHeader = new HttpHeaders();

        Cookie inCorrectCookie = new Cookie("hello","test");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken);
        Cookie cookie = new Cookie("refresh-token",refreshToken);

        inCorrectHeaders.add(emptyHeader);
        inCorrectHeaders.add(headers);
        inCorrectCookies.add(cookie);
        inCorrectCookies.add(inCorrectCookie);
        //when & then
        for(int i=0;i<inCorrectHeaders.size();i++){
            ResultActions results = this.mockMvc.perform(
                    get("/api/auth/reissue")
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(inCorrectHeaders.get(i))
                            .cookie(inCorrectCookies.get(i))
            );
            results.andExpect(status().isBadRequest());
        }
    }
}