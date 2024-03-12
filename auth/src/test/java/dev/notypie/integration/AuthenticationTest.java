package dev.notypie.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.base.SpringMockTest;
import dev.notypie.builders.UserRegisterDtoBuilder;
import dev.notypie.dao.UsersRepository;
import dev.notypie.domain.Users;
import dev.notypie.dto.UserRegisterDto;
import dev.notypie.exchanger.UserInfoExchanger;
import dev.notypie.jwt.dto.LoginRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@Tag("auth")
@ActiveProfiles({"jwt","disable-axon"})
public class AuthenticationTest extends SpringMockTest {

    @Autowired
    UsersRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    private Users user;
    private LoginRequestDto loginRequestDto;
    private UserRegisterDto registerDto;

    @BeforeEach
    void setUp(){
        UserRegisterDtoBuilder builder = new UserRegisterDtoBuilder();
        this.registerDto = builder.build();//Generate default user.
        this.user = UserInfoExchanger.exchangeToUsers(this.registerDto);
        this.loginRequestDto = UserInfoExchanger.exchangeToLoginRequestDto(this.registerDto);
    }

    @Test
    @DisplayName("[app.Auth] Authentication Process successfully works.")
    void authenticationSuccess() throws Exception{
        //given
        //when
        ResultActions registered = this.mockMvc.perform(
                post("/api/auth/register")
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsBytes(this.registerDto))
        );
        ResultActions result =this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(this.loginRequestDto))
                .with(csrf())
        );
        //then
        registered.andExpect(status().is2xxSuccessful());
        result.andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("[app.Auth] Authentication Failed when not Authorized.")
    void authenticationFailed() throws Exception{
        //given
        LoginRequestDto dto = LoginRequestDto.builder()
                .userId(this.registerDto.getUserId())
                .password("INVALID_PASSWORD")
                .build();
        LoginRequestDto dto2 = LoginRequestDto.builder()
                .userId("THIS_USER_IS_NOT_EXISTS")
                .password(this.registerDto.getPassword())
                .build();
        //when
        ResultActions registered = this.mockMvc.perform(
                post("/api/auth/register")
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsBytes(this.registerDto))
        );
        ResultActions unRegisteredUser = this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(dto2))
                .with(csrf()));
        ResultActions invalidPassword = this.mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsBytes(dto))
                        .with(csrf()));
        //then
        unRegisteredUser.andExpect(status().isUnauthorized());
        invalidPassword.andExpect(status().isUnauthorized());
    }
}
