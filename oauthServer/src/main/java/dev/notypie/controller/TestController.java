package dev.notypie.controller;

import dev.notypie.jwt.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/users")
@RestController
public class TestController {

    //This is only for test. remove future.
    @GetMapping("/me")
    public UserDto whoAmI(){
        return UserDto.builder().userId("testUserId").build();
    }
}
