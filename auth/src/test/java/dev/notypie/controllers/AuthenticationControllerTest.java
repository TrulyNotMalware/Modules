package dev.notypie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.application.UserCRUDService;
import dev.notypie.base.ControllerTest;
import dev.notypie.builders.UserRegisterDtoBuilder;
import dev.notypie.configurations.SecurityConfiguration;
import dev.notypie.domain.Users;
import dev.notypie.dto.UserRegisterDto;
import dev.notypie.exchanger.UserInfoExchanger;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@Tag("auth")
@WebMvcTest(controllers = AuthenticationController.class)
@Import(SecurityConfiguration.class) //WebSecurityCustomizer import.
public class AuthenticationControllerTest extends ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserCRUDService userService;

    private UserRegisterDtoBuilder dtoBuilder;
    private Users user;
    private UserRegisterDto register;

    //Initialize
    @BeforeEach
    void setUp(){
        //Create Default Users for test
        this.dtoBuilder = new UserRegisterDtoBuilder();
        this.register = this.dtoBuilder.build();
        this.user = UserInfoExchanger.exchangeToUsers(this.register);

        //register default users.
        Mockito.when(this.userService.register(any(UserRegisterDto.class))).thenReturn(UserInfoExchanger.exchangeToUserDto(this.user));
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
}