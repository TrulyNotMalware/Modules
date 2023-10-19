package dev.notypie.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.base.SpringMockTest;
import dev.notypie.builders.MockUserBuilders;
import dev.notypie.builders.UserRegisterDtoBuilder;
import dev.notypie.dao.UsersRepository;
import dev.notypie.domain.Users;
import dev.notypie.dto.UserRegisterDto;
import dev.notypie.exchanger.UserInfoExchanger;
import dev.notypie.jwt.dto.LoginRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ActiveProfiles("jwt")
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
    void authenticationSuccess() throws Exception{
        //given
        //Insert user.
        Users insertUser = this.repository.save(user);
        //when
        Users selectedUser = this.repository.findByIdWithException(insertUser.getId());
        ResultActions result =this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(this.loginRequestDto))
                .with(csrf())
        );
        //then
        Assertions.assertNull(this.user.getId());
        Assertions.assertEquals(selectedUser.getUserId(), this.user.getUserId());
        Assertions.assertNotNull(selectedUser.getId());
        result.andExpect(status().is2xxSuccessful());
    }
}
